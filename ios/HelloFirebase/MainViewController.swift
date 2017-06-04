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
        } else {
            let tabBarViewController = R.storyboard.main.tabBar()!
            displayContentViewController(tabBarViewController)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func replaceTabBarViewController(with fromViewController: UIViewController) {
        let toViewController = R.storyboard.main.tabBar()!
        
        fromViewController.willMove(toParentViewController: nil)
        addChildViewController(toViewController)
        
        transition(from: fromViewController,
                   to: toViewController,
                   duration: 0.25,
                   options: [.curveEaseInOut, .transitionCrossDissolve],
                   animations: nil) { _ in
                    fromViewController.removeFromParentViewController()
                    toViewController.didMove(toParentViewController: self)
                    print("Replaced TabBarController")
        }
    }
    
    private func displayContentViewController(_ viewController: UIViewController) {
        addChildViewController(viewController)
        viewController.view.frame = self.view.bounds
        self.view.addSubview(viewController.view)
        viewController.didMove(toParentViewController: self)
    }
}

