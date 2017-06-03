//
// This is a generated file, do not edit!
// Generated by R.swift, see https://github.com/mac-cain13/R.swift
//

import Foundation
import Rswift
import UIKit

/// This `R` struct is generated and contains references to static resources.
struct R: Rswift.Validatable {
  fileprivate static let applicationLocale = hostingBundle.preferredLocalizations.first.flatMap(Locale.init) ?? Locale.current
  fileprivate static let hostingBundle = Bundle(for: R.Class.self)
  
  static func validate() throws {
    try intern.validate()
  }
  
  /// This `R.color` struct is generated, and contains static references to 0 color palettes.
  struct color {
    fileprivate init() {}
  }
  
  /// This `R.file` struct is generated, and contains static references to 1 files.
  struct file {
    /// Resource file `GoogleService-Info.plist`.
    static let googleServiceInfoPlist = Rswift.FileResource(bundle: R.hostingBundle, name: "GoogleService-Info", pathExtension: "plist")
    
    /// `bundle.url(forResource: "GoogleService-Info", withExtension: "plist")`
    static func googleServiceInfoPlist(_: Void = ()) -> Foundation.URL? {
      let fileResource = R.file.googleServiceInfoPlist
      return fileResource.bundle.url(forResource: fileResource)
    }
    
    fileprivate init() {}
  }
  
  /// This `R.font` struct is generated, and contains static references to 0 fonts.
  struct font {
    fileprivate init() {}
  }
  
  /// This `R.image` struct is generated, and contains static references to 0 images.
  struct image {
    fileprivate init() {}
  }
  
  /// This `R.nib` struct is generated, and contains static references to 0 nibs.
  struct nib {
    fileprivate init() {}
  }
  
  /// This `R.reuseIdentifier` struct is generated, and contains static references to 0 reuse identifiers.
  struct reuseIdentifier {
    fileprivate init() {}
  }
  
  /// This `R.segue` struct is generated, and contains static references to 2 view controllers.
  struct segue {
    /// This struct is generated for `EmailSignInViewController`, and contains static references to 1 segues.
    struct emailSignInViewController {
      /// Segue identifier `close`.
      static let close: Rswift.StoryboardSegueIdentifier<UIKit.UIStoryboardSegue, EmailSignInViewController, UIKit.UIViewController> = Rswift.StoryboardSegueIdentifier(identifier: "close")
      
      /// Optionally returns a typed version of segue `close`.
      /// Returns nil if either the segue identifier, the source, destination, or segue types don't match.
      /// For use inside `prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)`.
      static func close(segue: UIKit.UIStoryboardSegue) -> Rswift.TypedStoryboardSegueInfo<UIKit.UIStoryboardSegue, EmailSignInViewController, UIKit.UIViewController>? {
        return Rswift.TypedStoryboardSegueInfo(segueIdentifier: R.segue.emailSignInViewController.close, segue: segue)
      }
      
      fileprivate init() {}
    }
    
    /// This struct is generated for `GoogleSignInViewController`, and contains static references to 2 segues.
    struct googleSignInViewController {
      /// Segue identifier `Sign In`.
      static let signIn: Rswift.StoryboardSegueIdentifier<UIKit.UIStoryboardSegue, GoogleSignInViewController, UIKit.UINavigationController> = Rswift.StoryboardSegueIdentifier(identifier: "Sign In")
      /// Segue identifier `Sign Up`.
      static let signUp: Rswift.StoryboardSegueIdentifier<UIKit.UIStoryboardSegue, GoogleSignInViewController, UIKit.UINavigationController> = Rswift.StoryboardSegueIdentifier(identifier: "Sign Up")
      
      /// Optionally returns a typed version of segue `Sign In`.
      /// Returns nil if either the segue identifier, the source, destination, or segue types don't match.
      /// For use inside `prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)`.
      static func signIn(segue: UIKit.UIStoryboardSegue) -> Rswift.TypedStoryboardSegueInfo<UIKit.UIStoryboardSegue, GoogleSignInViewController, UIKit.UINavigationController>? {
        return Rswift.TypedStoryboardSegueInfo(segueIdentifier: R.segue.googleSignInViewController.signIn, segue: segue)
      }
      
      /// Optionally returns a typed version of segue `Sign Up`.
      /// Returns nil if either the segue identifier, the source, destination, or segue types don't match.
      /// For use inside `prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)`.
      static func signUp(segue: UIKit.UIStoryboardSegue) -> Rswift.TypedStoryboardSegueInfo<UIKit.UIStoryboardSegue, GoogleSignInViewController, UIKit.UINavigationController>? {
        return Rswift.TypedStoryboardSegueInfo(segueIdentifier: R.segue.googleSignInViewController.signUp, segue: segue)
      }
      
      fileprivate init() {}
    }
    
    fileprivate init() {}
  }
  
  /// This `R.storyboard` struct is generated, and contains static references to 2 storyboards.
  struct storyboard {
    /// Storyboard `LaunchScreen`.
    static let launchScreen = _R.storyboard.launchScreen()
    /// Storyboard `Main`.
    static let main = _R.storyboard.main()
    
    /// `UIStoryboard(name: "LaunchScreen", bundle: ...)`
    static func launchScreen(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.launchScreen)
    }
    
    /// `UIStoryboard(name: "Main", bundle: ...)`
    static func main(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.main)
    }
    
    fileprivate init() {}
  }
  
  /// This `R.string` struct is generated, and contains static references to 0 localization tables.
  struct string {
    fileprivate init() {}
  }
  
  fileprivate struct intern: Rswift.Validatable {
    fileprivate static func validate() throws {
      try _R.validate()
    }
    
    fileprivate init() {}
  }
  
  fileprivate class Class {}
  
  fileprivate init() {}
}

struct _R: Rswift.Validatable {
  static func validate() throws {
    try storyboard.validate()
  }
  
  struct nib {
    fileprivate init() {}
  }
  
  struct storyboard: Rswift.Validatable {
    static func validate() throws {
      try main.validate()
    }
    
    struct launchScreen: Rswift.StoryboardResourceWithInitialControllerType {
      typealias InitialController = UIKit.UIViewController
      
      let bundle = R.hostingBundle
      let name = "LaunchScreen"
      
      fileprivate init() {}
    }
    
    struct main: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = MainViewController
      
      let bundle = R.hostingBundle
      let googleSignIn = StoryboardViewControllerResource<GoogleSignInViewController>(identifier: "GoogleSignIn")
      let name = "Main"
      let tabBar = StoryboardViewControllerResource<TabBarController>(identifier: "tabBar")
      
      func googleSignIn(_: Void = ()) -> GoogleSignInViewController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: googleSignIn)
      }
      
      func tabBar(_: Void = ()) -> TabBarController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: tabBar)
      }
      
      static func validate() throws {
        if _R.storyboard.main().googleSignIn() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'googleSignIn' could not be loaded from storyboard 'Main' as 'GoogleSignInViewController'.") }
        if _R.storyboard.main().tabBar() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'tabBar' could not be loaded from storyboard 'Main' as 'TabBarController'.") }
      }
      
      fileprivate init() {}
    }
    
    fileprivate init() {}
  }
  
  fileprivate init() {}
}