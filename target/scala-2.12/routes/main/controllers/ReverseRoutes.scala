// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Damian/Desktop/Studia/IV SEMESTR/Praktyki/Kainos/testy/project/cryptocurrency/conf/routes
// @DATE:Fri Jun 01 13:51:01 CEST 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def formTest(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/formTest")
    }
  
    // @LINE:10
    def update(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/update")
    }
  
    // @LINE:12
    def fetchTest(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/fetchTest")
    }
  
    // @LINE:8
    def diagram(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:16
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
