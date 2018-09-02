//
//  AddToListViewController.swift
//  Happy Ivy
//  www.youtube.com/watch?v=gUhhFPTKCrE
//  stackoverflow.com/questions/33788274/how-to-change-the-line-color-of-a-uidatepicker
//  stackoverflow.com/questions/39939557/how-to-customize-uitextfield
//  stackoverflow.com/questions/24126678/close-ios-keyboard-by-touching-anywhere-using-swift
//  www.youtube.com/watch?v=9jtX0xEuQqs
//  Created by Keke Wu on 3/5/18.
//  Copyright © 2018 Keke Wu. All rights reserved.

import UIKit
import RealmSwift

class AddToListViewController: UIViewController, UITextFieldDelegate {
    
    var realm: Realm! //An instance of Realm DB
    var todoList: Results<ToDoList> {
        get {
            return realm.objects(ToDoList.self) //Returns all grocery obejcts from Realm
        }
    }
    
    //Perform saveSegue
    @IBAction func saveBtn(_ sender: UIBarButtonItem) {
        performSegue(withIdentifier: "saveSegue", sender: self)
    }
    @IBAction func cancleBtn(_ sender: UIBarButtonItem) {
        performSegue(withIdentifier: "unwindSegue", sender: self)
    }
    @IBOutlet weak var AddTextField: UITextField!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var imgImg: UIImageView!
    
    @IBOutlet weak var monthLabel: UILabel!
    @IBOutlet weak var dayLabel: UILabel!
    @IBOutlet weak var yearLabel: UILabel!
    
    
    var image = UIImage()
    var imageName: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getCurrentDate()
        getSingle()
        
        //Initiate Realm
        realm = try! Realm()
        
        //Print filepath
        print(Realm.Configuration.defaultConfiguration.fileURL!)
        
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "Timer_BG")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)
        
        //Textfield Delegate
        AddTextField.delegate = self
    
        //Set TextField Style
        AddTextField.borderStyle = .none
        AddTextField.layoutIfNeeded()
        let border = CALayer()
        let width = CGFloat(2.0)
        border.borderColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 0.8).cgColor
        print(AddTextField.frame)
        border.frame = CGRect(x: 0, y: AddTextField.frame.size.height - width, width:  AddTextField.frame.size.width, height: AddTextField.frame.size.height)
        border.borderWidth = width
        AddTextField.layer.addSublayer(border)
        AddTextField.layer.masksToBounds = true
        
        //DatePicker text color
        datePicker.setValue(UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0), forKey:"textColor")
        
        //DatePicker Indicator color
        for subview in self.datePicker.subviews {
            if subview.frame.height <= 5 {
                subview.backgroundColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0)
                subview.tintColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0)
                subview.layer.borderColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0).cgColor
                subview.layer.borderWidth = 1            }
        }
        
        if let pickerView = self.datePicker.subviews.first {
            for subview in pickerView.subviews {
                if subview.frame.height <= 5 {
                    subview.backgroundColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0)
                    subview.tintColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0)
                    subview.layer.borderColor = UIColor.init(red:0.72 , green: 0.58, blue: 0.75, alpha: 1.0).cgColor
                    subview.layer.borderWidth = 1
                }
            }
        }
    }
    

    
    
    //Dismiss the keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        AddTextField.resignFirstResponder()
        return true
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
        monthLabel.text = month[calendar.component(.month, from: date)-1] //turn 3 into March
        dayLabel.text = String(day)
        yearLabel.text = String(year)
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        if let name = imageName{
            imgImg.image = UIImage(named: name)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}



