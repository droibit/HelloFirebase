//
//  User.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/31/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import Foundation

struct User {
    
    var userName: String = ""
    
    var email: String = ""
    
    func toDictionary() -> Dictionary<String, String> {
        return [
            "userName": userName,
            "email": email
        ]
    }
}
