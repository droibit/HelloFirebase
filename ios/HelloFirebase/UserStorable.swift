//
//  UserStorable.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/30/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import Foundation
import Firebase


protocol UserStorable {
    
    func storeUser(userId: String, email: String, completion: @escaping (Bool) -> Void)
}

extension UserStorable {
    
    func storeUser(userId: String, email: String, completion: @escaping (Bool) -> Void) {
        let databaseRef = Database.database().reference()
        databaseRef.child("users").child(userId).observeSingleEvent(of: .value, with: { snapshot in
            guard !snapshot.exists() else {
                let user = snapshot.value as! [String : Any]
                print("alredy exists: \(user["userName"]!)")
                completion(true)
                return
            }
            
            let userName = email.components(separatedBy: "@").first ?? email
            let user = User(userName: userName, email: email)
            databaseRef.child("users").child(userId)
                .setValue(user.toDictionary(), withCompletionBlock: { error, snapshot in
                        if let error = error {
                            print("user: \(userName), error: \(error.localizedDescription)")
                            completion(false)
                            return
                        }
                    
                        print("stored user: \(userName)")
                        completion(true)
                    })
            })
    }
}
