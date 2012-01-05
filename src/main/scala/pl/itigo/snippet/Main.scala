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

object Main {
	
	var firstName = ""
	var lastName = ""
	var email = ""
	
	def form = "name=first_name" #> text("", firstName = _) &
		"name=last_name" #> text("", lastName = _) &
		"name=email" #> text(""	, email = _) &
		"type=submit" #> submit("submit", doSubmit)
		
	def doSubmit() = {
			if(firstName.length <3) S.error("first_name", "Name too short")
			if(lastName.length <3) S.error("last_name", "Name too short")
			if(email.length <3) S.error("email", "email too short")
			println("first name: " + firstName)
			println("last name: "+ lastName)
			println("email: " + email)
		}
		
		
	def showSubmit() = {
		"#firstName" #> <span>{firstName}</span> &
		"#lastName" #> <span>{lastName}</span> &
		"#email" #> <span>{email}</span>
	}
	
	
	
	def ajaxForm() = "name=first_name" #> text("", firstName = _) &
		"name=last_name" #> text("", lastName = _) &
		"name=email" #> (text("", email = _) ++ SHtml.hidden(doAjaxSubmit))
	
	def doAjaxSubmit() = {
			Thread.sleep(500)
			println(firstName)
			Alert("first name:" + firstName)
	}
}