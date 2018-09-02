//
//  ToDoViewController.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/3/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//

import UIKit
import RealmSwift

class ToDoViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    

    @IBOutlet weak var tableView: UITableView!
    var realm: Realm! //An instance of Realm DB
    var todoList: Results<ToDoList> {
        get {
            return realm.objects(ToDoList.self) //Returns all grocery obejcts from Realm
        }
    }
    
    
    
    var iconNum = [String]()
    override func viewDidLoad() {
        super.viewDidLoad()
        loadData()
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "Todo_BG")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.tableView.backgroundView = backgroundImage
        // Do any additional setup after loading the view.
     
        navigationItem.hidesBackButton = true
        //Initialize the realm variable
        do{
            realm = try Realm()
        }catch let error {
            print(error.localizedDescription)
        }
    
    }

    
    func loadData(){
        //Tab Bar Icons
        let tabBar: UITabBar = tabBarController!.tabBar
        let tabBarItem1: UITabBarItem = tabBar.items![0]
        let tabBarItem2: UITabBarItem = tabBar.items![1]
        let tabBarItem3: UITabBarItem = tabBar.items![2]
        let tabBarItem4: UITabBarItem = tabBar.items![3]
        tabBarItem1.image = UIImage(named:"tab2_1")
        tabBarItem2.image = UIImage(named:"tab2_2")
        tabBarItem3.image = UIImage(named:"tab2_3")
        tabBarItem4.image = UIImage(named:"tab2_4")
    }
    
    
    //Call loadData() again
    override func viewWillAppear(_ animated: Bool) {
        loadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return todoList.count
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
     func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let item = todoList[indexPath.row]
        cell.textLabel!.text = item.things
        cell.accessoryType = item.done ? .checkmark : .none
        return cell
    }
    
     func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = todoList[indexPath.row]
        try! self.realm.write {
            item.done = !item.done
        }
        tableView.reloadRows(at: [indexPath], with: .automatic)
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


