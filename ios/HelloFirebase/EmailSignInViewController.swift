//
//  EmailSignInViewController.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/28/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import UIKit
import Firebase
import SVProgressHUD

class EmailSignInViewController: UIViewController, UserStorable {
    
    typealias Action = (String, String,@escaping FirebaseAuth.AuthResultCallback) -> Void
    
    enum SignInType: String {
        case SignUp = "Sign Up"
        case SignIn = "Sign In"
    }
    
    
    @IBOutlet weak var actionButton: UIButton!
    
    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var passwordTextField: UITextField!
    
    var signInType: SignInType! = nil {
        willSet {
            switch newValue! {
            case .SignUp:
                action = self.signUp
            case .SignIn:
                action = self.signIn
            }
        }
    }
    
    var completed = false
    
    private var action: Action!
    
    deinit {
        print("deinit \(className)")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        
        let navigationViewController = parent as! UINavigationController
        navigationViewController.navigationBar.topItem?.title = signInType.rawValue
        
        actionButton.setTitle(signInType.rawValue, for: .normal)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func didTapActionButton(_ sender: Any) {
        guard let email = emailTextField.text,
                let password = passwordTextField.text, !password.isEmpty && !email.isEmpty else {
                    showError(message: "email/password can't be empty", dismissDelay: 1.0)
            return
        }
  
        showProgress()
        
        action(email, password) { (firebaseUser, error) in
            if let error = error {
                print("Error: \(error.localizedDescription)")
                self.showError(message: error.localizedDescription, dismissDelay: 2.0)
                return
            }
            
            guard let user = firebaseUser else {
                self.showError(message: "Failed", dismissDelay: 1.0)
                return
            }
            
            print("SignIn/Up User: \(user.providerData.first!.email!)")
            self.storeUser(userId: user.uid, email: user.email!) { successful in
                if !successful {
                    self.showError(message: "Failed", dismissDelay: 1.0)
                    return
                }
                self.completed = successful
                
                self.dismissProgress {
                    self.performSegue(withIdentifier: R.segue.emailSignInViewController.close, sender: nil)
                }
            }
        }
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
}

extension EmailSignInViewController {
    
    func signUp(email: String, password: String, completion: @escaping FirebaseAuth.AuthResultCallback) {
        Auth.auth().createUser(withEmail: email, password: password, completion: completion)
    }
    
    func signIn(email: String, password: String, completion: @escaping FirebaseAuth.AuthResultCallback) {
        Auth.auth().signIn(withEmail: email, password: password, completion: completion)
    }
}
