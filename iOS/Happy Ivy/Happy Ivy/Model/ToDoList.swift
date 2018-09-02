//
//  ToDoList.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/12/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//

import Foundation
import RealmSwift
class ToDoList: Object {
    @objc dynamic var iconName = ""
    @objc dynamic var things = ""
    @objc dynamic var time = ""
    @objc dynamic var done = false
}
