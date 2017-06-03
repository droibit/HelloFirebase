//
//  SignInExtentions.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 6/3/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import Foundation
import SVProgressHUD

extension UserStorable where Self : UIViewController {
    
    func showProgress() {
        DispatchQueue.main.async {
            SVProgressHUD.setDefaultMaskType(.black)
            SVProgressHUD.show()
        }
    }
    
    func showError(message: String, dismissDelay: TimeInterval) {
        DispatchQueue.main.async {
            SVProgressHUD.setDefaultMaskType(.none)
            SVProgressHUD.showError(withStatus: message)
            SVProgressHUD.dismiss(withDelay: dismissDelay)
        }
    }
    
    func dismissProgress(completion: (()->Void)? = nil) {
        DispatchQueue.main.async {
            SVProgressHUD.dismiss {
                completion?()
            }
        }
    }
}
