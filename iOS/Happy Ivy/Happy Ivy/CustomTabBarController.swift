//
//  CustomeTabBarController.swift
//  Happy Ivy
//  stackoverflow.com/questions/42569589/ios-uitabbaritem-image-selectedimage-and-image-show-incorrect
//  Created by Keke Wu on 3/9/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//  stackoverflow.com/questions/27589797/transparent-uitabbar

import UIKit

class CustomTabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()
    
        //Transparent TabBar BG
        self.tabBar.backgroundColor = UIColor.clear
        self.tabBar.backgroundImage = UIImage()
        self.tabBar.shadowImage = UIImage()
        self.tabBarItem.selectedImage?.withRenderingMode(.alwaysOriginal)

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
