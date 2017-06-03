//
//  GoogleSignInViewController.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/22/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import UIKit
import Firebase
import GoogleSignIn
import SVProgressHUD

class GoogleSignInViewController: UIViewController, UserStorable {
    
    @IBOutlet weak var googleSignInButton: GIDSignInButton!
    
    deinit {
        print("deinit \(className)")
        
        let gooleSignIn = GIDSignIn.sharedInstance()!
        gooleSignIn.uiDelegate = nil
        gooleSignIn.delegate = nil
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typically from a nib.
        let gooleSignIn = GIDSignIn.sharedInstance()!
        gooleSignIn.uiDelegate = self
        gooleSignIn.delegate = self
        
        googleSignInButton.style = .wide
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func unwind(segue: UIStoryboardSegue) {
        print("identifier:\(segue.identifier ?? "NULL"), source: \(segue.source.className)")
        if segue.identifier == "close" {
            let viewController = segue.source as! EmailSignInViewController
            print("SignIn/Up completed: \(viewController.completed)")
            if viewController.completed {
                didSignIn()
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let signinType = EmailSignInViewController.SignInType(rawValue: segue.identifier ?? "") {
            let navigationViewController = segue.destination as! UINavigationController
            let emailSignInViewController = navigationViewController.topViewController as! EmailSignInViewController
            emailSignInViewController.signInType = signinType
        }
    }
    
    func didSignIn() {
        let parentViewController = parent as! MainViewController
        print("parent of GoogleSignInVC: \(parentViewController.className)")
        parentViewController.replaceTabBarViewController(with: self)
    }
}

extension GoogleSignInViewController: GIDSignInDelegate, GIDSignInUIDelegate {

    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error?) {
        if let e = error {
            print("Google SignIn Error: \(e.localizedDescription)")
            return
        }
        print("Google SignIn Successed: \(user!.profile.email!)")
        
        guard let authentication = user.authentication else {
            return
        }
        let credential = GoogleAuthProvider.credential(withIDToken: authentication.idToken,
                                                       accessToken: authentication.accessToken)
        
        signInFirebase(with: credential)
    }
    
    func sign(inWillDispatch signIn: GIDSignIn!, error: Error?) {
        print("sign:inWillDispatch")
    }
    
    func sign(_ signIn: GIDSignIn!, present viewController: UIViewController!) {
        print("signIn:present")
        present(viewController, animated: true, completion: nil)
    }
    
    func sign(_ signIn: GIDSignIn!, dismiss viewController: UIViewController!) {
        print("signIn:dismiss")
        viewController.dismiss(animated: true, completion: nil)
    }
    
    private func signInFirebase(with credential: AuthCredential) {
        showProgress()

        Auth.auth().signIn(with: credential) { firebaseUser, error in
            if let error = error {
                print("Firebase SignIn Error \(error.localizedDescription)")
                self.showError(message: error.localizedDescription, dismissDelay: 2.0)
                return
            }
            
            guard let user = firebaseUser else {
                self.showError(message: "Failed", dismissDelay: 1.0)
                return
            }
            print("Firebase signedIn User: \(user.providerData.first!.email!)")
            
            self.storeUser(userId: user.uid, email: user.email!) { successful in
                if !successful {
                    self.showError(message: "Failed", dismissDelay: 1.0)
                    return
                }
                self.dismissProgress {
                    self.didSignIn()
                }
            }
        }
    }
}
