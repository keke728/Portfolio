//
//  GoalsViewController.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/3/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//  medium.com/@mimicatcodes/create-unwind-segues-in-swift-3-8793f7d23c6f

import UIKit

var iconSets = [String]()

class GoalsViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout, UITabBarDelegate{
        
    //Set cell positions for collectionview
    let sectionInsets = UIEdgeInsets(top:150.0, left: 20.0, bottom: 20.0, right: 20.0)
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return sectionInsets
    }
    
    //UnwindSegue function
    @IBAction func unwindSegue(segue:UIStoryboardSegue){
    }
    

    @IBAction func moreBtn(_ sender: UIButton) {
    }
    @IBOutlet weak var month_label: UILabel!
    @IBOutlet weak var day_label: UILabel!
    @IBOutlet weak var year_label: UILabel!
    
    @IBOutlet weak var CollectionView: UICollectionView!
    
      func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return iconSets.count
      }
    
      func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        //Set different icons for collectionview cells
        let cell = CollectionView.dequeueReusableCell(withReuseIdentifier: "CustomCell",for: indexPath) as! CustomCollectionViewCell
        cell.cell_1.image = UIImage(named:iconSets[indexPath.row])
        return cell
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
        month_label.text = month[calendar.component(.month, from: date)-1] //turn 3 into March
        day_label.text = String(day)
        year_label.text = String(year)
    }
    
    //Passing icon to AddToListViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "AddGoals" {
            let indexPath = CollectionView?.indexPath(for: sender as! CustomCollectionViewCell)
            let detailVC = segue.destination as! AddToListViewController
            detailVC.imageName = iconSets[(indexPath?.row)!]
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadData()
        
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "Goals_BG")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)
        // Do any additional setup after loading the view.
        CollectionView.delegate = self
        CollectionView.dataSource = self
        
        //Loop iconSets images
        for i in 1...19{
            iconSets.append("icon" + String(i))
        }

        getCurrentDate()
        getSingle()

    }
    
    func loadData(){
        //Tab Bar Icons
        let tabBar: UITabBar = tabBarController!.tabBar
        let tabBarItem1: UITabBarItem = tabBar.items![0]
        let tabBarItem2: UITabBarItem = tabBar.items![1]
        let tabBarItem3: UITabBarItem = tabBar.items![2]
        let tabBarItem4: UITabBarItem = tabBar.items![3]
        tabBarItem1.image = UIImage(named:"tab1_1")
        tabBarItem2.image = UIImage(named:"tab1_2")
        tabBarItem3.image = UIImage(named:"tab1_3")
        tabBarItem4.image = UIImage(named:"tab1_4")
    }
    
    //Call loadData() again
    override func viewWillAppear(_ animated: Bool) {
        loadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
