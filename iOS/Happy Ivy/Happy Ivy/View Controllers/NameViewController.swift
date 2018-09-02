//
//  NameViewController.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/10/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//

import UIKit

class NameViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var nameTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "name_bg")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)
        // Do any additional setup after loading the view.
        nameTextField.delegate = self
    }

    //Enable Done Button Action
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        nameTextField.resignFirstResponder()
        performSegue(withIdentifier: "showPlant", sender: self)
        return true
    }

    //Pass Name to PlantView
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let plantViewController = segue.destination as? PlantViewController {
            plantViewController.text = nameTextField.text
        }
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
