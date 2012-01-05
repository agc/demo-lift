package pl.itigo.snippet
import pl.itigo.model._
import scala.xml._
import net.liftweb._
import http._
import common._
import util.Helpers._
import util.ClearClearable
import js.JsCmds._
import js.JsCmd
import js.jquery.JqJsCmds._
import SHtml._
import mapper.By
import net.liftweb.http.js.JE.Call

object Index {
	
	private var firstName = ""
	private var lastName = ""
		
	def form() = {
		"name=firstName" #> text(firstName, firstName = _) &
		"name=lastName" #> text(lastName, lastName = _) &
		"type=submit" #> submit("submit", doSubmit)
		
	}
	
	def doSubmit() {
		println("name" + firstName)
		println("last name: "+ lastName)
	}
	
	def show = "#firstName" #> firstName &
		"#lastName" #> lastName
}