//
//  ShelvesViewController.swift
//  Happy Ivy
//  Created by Keke Wu on 3/3/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//  stackoverflow.com/questions/46577217/how-to-reload-a-view-controller-when-back-from-another-view-using-tab-bar

import UIKit

class ShelvesViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "pots_bg")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)
        loadData() //Separate function to refresh viewdidload
    }
    
    //Separate Function to Refresh ViewDidLoad
    func loadData(){
        //Tab Bar Icons
        let tabBar: UITabBar = tabBarController!.tabBar
        let tabBarItem1: UITabBarItem = tabBar.items![0]
        let tabBarItem2: UITabBarItem = tabBar.items![1]
        let tabBarItem3: UITabBarItem = tabBar.items![2]
        let tabBarItem4: UITabBarItem = tabBar.items![3]
        tabBarItem1.image = UIImage(named:"tab4_1")
        tabBarItem2.image = UIImage(named:"tab4_2")
        tabBarItem3.image = UIImage(named:"tab4_3")
        tabBarItem4.image = UIImage(named:"tab4_4")
        
    }
   
    //Call loadData() again
    override func viewWillAppear(_ animated: Bool) {
        loadData()
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
