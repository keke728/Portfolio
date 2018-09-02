//
//  PlantViewController.swift
//  Happy Ivy
//
//  Created by Keke Wu on 3/10/18.
//  Copyright © 2018 Keke Wu. All rights reserved.
//

import UIKit

class PlantViewController: UIViewController {
    
    var text: String? = nil
    @IBOutlet weak var nameLabel: UILabel!
    @IBAction func GoalBtn(_ sender: UIButton) {
        performSegue(withIdentifier: "goToTab", sender: self)
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        nameLabel.text = text
        // Do any additional setup after loading the view.
        //Set Background Image
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
        backgroundImage.image = UIImage(named: "plant_bg")
        backgroundImage.contentMode = UIViewContentMode.scaleAspectFill
        self.view.insertSubview(backgroundImage, at: 0)
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
