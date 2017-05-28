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

class EmailSignInViewController: UIViewController {
    
    typealias Task = (String, String, @escaping FirebaseAuth.AuthResultCallback) -> Void
    
    enum SignInType: String {
        case SignUp = "Sign Up"
        case SignIn = "Sign In"
    }
    
    var signInType: SignInType! = nil {
        willSet {
            switch newValue! {
            case .SignUp:
                task = self.signUp
            case .SignIn:
                task = self.signIn
            }
        }
    }
    
    var completed = false
    
    private var task: Task!
    
    fileprivate let auth = Auth.auth()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let navigationViewController = parent as! UINavigationController
        navigationViewController.navigationBar.topItem?.title = signInType.rawValue

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
        auth.createUser(withEmail: email, password: password, completion: completion)
    }
    
    func signIn(email: String, password: String, completion: @escaping FirebaseAuth.AuthResultCallback) {
        auth.signIn(withEmail: email, password: password, completion: completion)
    }
    
    
}
