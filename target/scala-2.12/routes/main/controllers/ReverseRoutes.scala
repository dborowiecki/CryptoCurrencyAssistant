// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Damian/Desktop/Studia/IV SEMESTR/Praktyki/Kainos/testy/project/cryptocurrency/conf/routes
// @DATE:Thu May 31 11:08:23 CEST 2018

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

  
    // @LINE:8
    def diagram(dateFrom:String, dateTo:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("dateFrom", dateFrom)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("dateTo", dateTo)))
    }
  
    // @LINE:12
    def fetchTest(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/fetchTest")
    }
  
    // @LINE:10
    def update(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "diagram/update")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:15
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
