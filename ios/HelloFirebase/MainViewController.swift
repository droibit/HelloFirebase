//
//  ViewController.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/16/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import UIKit
import Firebase

class MainViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        if Auth.auth().currentUser == nil {
            let googleSignInViewController = R.storyboard.main.googleSignIn()
            displayContentViewController(googleSignInViewController!)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func displayContentViewController(_ viewController: UIViewController) {
        addChildViewController(viewController)
        viewController.view.frame = self.view.bounds
        self.view.addSubview(viewController.view)
        viewController.didMove(toParentViewController: self)
    }
}

