//
//  EmailSignInViewController.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/28/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import UIKit

class EmailSignInViewController: UIViewController {
    
    enum SignInType: String {
        case SignUp = "SignUp"
        case SignIn = "SignIn"
    }
    
    var signInType: SignInType!
    
    var completed = false

    override func viewDidLoad() {
        super.viewDidLoad()

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
