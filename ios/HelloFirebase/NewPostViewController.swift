//
//  PostViewController.swift
//  HelloFirebase
//
//  Created by Shinya Kumagai on 6/11/17.
//  Copyright © 2017 Shinya Kumagai. All rights reserved.
//

import UIKit
import Firebase
import SVProgressHUD

class NewPostViewController: UIViewController {

    @IBOutlet weak var titleText: UITextField!
    
    @IBOutlet weak var contentText: UITextView!
    
    private var databaseRef: DatabaseReference!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        databaseRef = Database.database().reference()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func didTapClose(_ sender: Any) {
        view.endEditing(true)
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func didTapSend(_ sender: Any) {
        view.endEditing(true)
        
        guard let title = titleText.text,
            let content = contentText.text, !title.isEmpty && !content.isEmpty else {
                showError(message: "You have to input title and content.", dismissDelay: 1.5)
                return
        }
        
        let userId = Auth.auth().currentUser?.uid
        
        showProgress()
        
        sendPost(userId: userId!, title: title, content: content) { error in
            if let error = error {
                print("posting error: \(error.localizedDescription)")
                self.showError(message: error.localizedDescription, dismissDelay: 2.0)
                return
            }
            print("success posting")
            
            self.dismissProgress {
                self.dismiss(animated: true, completion: nil)
            }
        }
    }
    
    private func sendPost(userId: String, title: String, content: String, completion: @escaping (Error?)->Void) {
        databaseRef.child("users").child(userId).observeSingleEvent(of: .value, with: { (snapshot) in
                let value = snapshot.value as! NSDictionary
                let userName = value["userName"] as! String
                let key = self.databaseRef.child("posts").childByAutoId().key
                let post = Post(uid: userId, author: userName, title: title, body: content).toDictionary()
                print("post: \(post)")
            
                let childUpdates = [
                    "/posts/\(key)": post,
                    "/user-posts/\(userId)/\(key)/": post
                ]
                self.databaseRef.updateChildValues(childUpdates, withCompletionBlock: { (error, ref) in
                    completion(error)
                })
            })
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
