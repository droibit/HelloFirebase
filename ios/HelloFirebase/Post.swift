//
//  Post.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 6/11/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import Foundation

struct Post {
    var uid: String
    
    var author: String
    
    var title: String
    
    var body: String
    
    var starCount: Int = 0
    
    var stars: Dictionary<String, Bool>? = nil
    
    init(uid: String, author: String, title: String, body: String) {
        self.uid = uid
        self.author = author
        self.title = title
        self.body = body
        self.starCount = 0
        self.stars = nil
    }
    
    func toDictionary() -> Dictionary<String, Any?> {
        return [
            "uid": uid,
            "author": author,
            "title": title,
            "body": body,
            "starCount": starCount,
            "stars": stars
        ]
    }
}
