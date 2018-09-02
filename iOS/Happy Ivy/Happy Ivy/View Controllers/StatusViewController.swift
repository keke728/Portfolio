//
//  StatusViewController.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/3/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//

import UIKit

class StatusViewController: UIViewController {

    @IBOutlet var mon_Label: UILabel!
    @IBOutlet var d_Label: UILabel!
    @IBOutlet var y_Label: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        loadData()
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "Status_BG")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)

        // Do any additional setup after loading the view.
        getCurrentDate()
        getSingle()
    
    }

    //Separate Function to Refresh ViewDidLoad
    func loadData(){
        //Tab Bar Icons
        let tabBar: UITabBar = tabBarController!.tabBar
        let tabBarItem1: UITabBarItem = tabBar.items![0]
        let tabBarItem2: UITabBarItem = tabBar.items![1]
        let tabBarItem3: UITabBarItem = tabBar.items![2]
        let tabBarItem4: UITabBarItem = tabBar.items![3]
        tabBarItem1.image = UIImage(named:"tab3_1")
        tabBarItem2.image = UIImage(named:"tab3_2")
        tabBarItem3.image = UIImage(named:"tab3_3")
        tabBarItem4.image = UIImage(named:"tab3_4")
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //Call loadData() again
    override func viewWillAppear(_ animated: Bool) {
        loadData()
    }
    
    
    //Get Current Date
    func getCurrentDate(){
        let formatter = DateFormatter()
        formatter.dateFormat = "LLLL, dd, yyyy"
    }
    
    
    //Get Single Date
    func getSingle(){
        let date = Date()
        let calendar = Calendar.current
        let year = calendar.component(.year, from: date) //get current year
        let month = calendar.shortMonthSymbols
        let day = calendar.component(.day, from: date)
        mon_Label.text = month[calendar.component(.month, from: date)-1] //turn 3 into March
        d_Label.text = String(day)
        y_Label.text = String(year)
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
