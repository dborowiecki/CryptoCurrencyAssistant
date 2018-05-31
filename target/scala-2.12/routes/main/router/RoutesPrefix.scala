// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Damian/Desktop/Studia/IV SEMESTR/Praktyki/Kainos/testy/project/cryptocurrency/conf/routes
// @DATE:Thu May 31 11:08:23 CEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
