//
//  CustomNavController.swift
//  Happy Ivy
//  Customize Navigationbar Color 
//  Created by Keke Wu on 3/7/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//  www.youtube.com/watch?v=Pjz_KU89FSY

import UIKit

class CustomNavController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationBar.shadowImage = UIImage()
        
        //Set BarButtonItem Color and Font
        UINavigationBar.appearance().tintColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0)
        let navigationFont = UIFont(name:"AveriaSansLibre-Regular",size:20)
        let navigationFontAttributes = [NSAttributedStringKey.font : navigationFont]
        UINavigationBar.appearance().titleTextAttributes = navigationFontAttributes
        UIBarButtonItem.appearance().setTitleTextAttributes(navigationFontAttributes, for: .normal)
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    



}
