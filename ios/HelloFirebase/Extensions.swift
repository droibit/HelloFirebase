//
//  Extensions.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 5/28/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import Foundation

extension NSObject {
    class var className: String {
        return String(describing: self)
    }
    
    var className: String {
        return type(of: self).className
    }
}
