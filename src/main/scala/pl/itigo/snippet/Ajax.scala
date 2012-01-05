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

object Ajax {
	
	var firstName = ""
	var lastName = ""
	var email = ""
	
	def ajaxForm() = "name=first_name" #> text("", firstName = _) &
		"name=last_name" #> text("", lastName = _) &
		"name=email" #> (text("", email = _) ++ SHtml.hidden(doAjaxSubmit))
	
	def doAjaxSubmit() = {
			Thread.sleep(500)
			println(firstName)
			Alert("first name:" + firstName) &
			SetHtml("firstName", <b>{firstName}</b>) &
			SetHtml("lastName", <b>{lastName}</b>) &
			SetHtml("email", <b>{email}</b>)
	}
}