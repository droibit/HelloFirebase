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

class GoogleSignInViewController: UIViewController {
    
    let sharedGoogleSignIn: GIDSignIn! = GIDSignIn.sharedInstance()
    
    let auth = Auth.auth()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typically from a nib.
        sharedGoogleSignIn.uiDelegate = self
        sharedGoogleSignIn.delegate = self
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

extension GoogleSignInViewController: GIDSignInDelegate, GIDSignInUIDelegate {

    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error?) {
        if let e = error {
            print("Google SignIn Error: \(e.localizedDescription)")
            return
        }
        print("Google SignIn Successed: \(user)")
        
        guard let authentication = user.authentication else {
            return
        }
        let credential = GoogleAuthProvider.credential(withIDToken: authentication.idToken,
                                                       accessToken: authentication.accessToken)
        
        signInFirebase(with: credential)
    }
    
    func sign(inWillDispatch signIn: GIDSignIn!, error: Error?) {
    }
    
    func sign(_ signIn: GIDSignIn!, present viewController: UIViewController!) {
        present(viewController, animated: true, completion: nil)
    }
    
    func sign(_ signIn: GIDSignIn!, dismiss viewController: UIViewController!) {
        viewController.dismiss(animated: true, completion: nil)
    }
    
    private func signInFirebase(with credential: AuthCredential) {
        // TODO: show progress
        auth.signIn(with: credential) { user, error in
            // TODO: hide progress
            if let e = error {
                print("Firebase SignIn Error \(e.localizedDescription)")
                return
            }
        }
    }
}
