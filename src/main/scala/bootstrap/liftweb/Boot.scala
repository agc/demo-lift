package bootstrap.liftweb

import net.liftweb._
import org.acidbits.rest._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import org.acidbits.model._
import org.acidbits.rest._

import net.liftweb.widgets.tablesorter.TableSorter

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("org.acidbits")
    /* for H2 console and other */
    LiftRules.passNotFoundToChain = true
    
    val loggedIn = If(() => User.loggedIn_?, () => S.redirectTo("/404"))
    
    val menus = List(Menu.i("Home") / "index" >> User.AddUserMenusAfter, // the simple way to declare a menu
      Menu.i("Simple Form") / "simpleform",
      Menu.i("Ajax Form") / "ajaxform" >> loggedIn,
      Menu.i("Lazy Load") / "lazyload",
      Menu.i("Parallel Rendering") / "parallel",
      Menu.i("Comet Chat") / "chat",
      Menu.i("Rest") / "rest" / ** >> Hidden,
      Menu.i("404") / "404" >> Hidden, 
      // more complex because this menu allows anything in the
      // static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))
    // Build SiteMap
    def sitemap = SiteMap(menus:_*)

    def sitemapMutators(sitemap: SiteMap) = User.sitemapMutator(sitemap)

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemapMutators(sitemap))
    
    // url rewriting
    LiftRules.statefulRewrite.append {
    	case RewriteRequest(ParsePath("post" :: id :: Nil,_,_,_),_,_) => {
    		RewriteResponse(List("/post/details"), Map("id" -> id))
    	}
    }
    
    // REST
    LiftRules.dispatch.append(UserService)
    
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)
    // Use HTML5 for rendering
//    LiftRules.htmlProperties.default.set((r: Req) =>
//    	new Html5Properties(r.userAgent)) 
    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }
}
