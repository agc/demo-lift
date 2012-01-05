package org.acidbits.rest
import net.liftweb._
import http.rest._
import http._
import js._
import org.acidbits.model.User
import json.JsonAST._
import json._
import util.JSONParser._
import mapper._
import scala.xml._
import util._
import Helpers._
import common._

object UserService extends RestHelper {

	def userListXml = {
	  <users>
		{User.findAll().map(_.toXml)}
	  </users>
	}
	
	def userXml(id: String) = {
	  tryo{id.toLong} match {
	    case Full(idLong) => User.find(By(User.id,idLong)).map(_.toXml) openOr <span>User not found</span>
	    case _ => <span style="color:red">I wanna Long</span>
	  }
	  
	}
	
	def userListHtml = {
	  <table>
		{User.findAll().map(_.toHtml)}
	  </table>
	}
	
	serve {
	   case Req("rest" :: "user" :: "xml" :: Nil, _, GetRequest) => userListXml
	   case Req("rest" :: "user" :: "html" :: Nil, _, GetRequest) => userListHtml
	   case Req("rest" :: "user" :: "onlyxml" :: Nil, "xml", GetRequest) => <xml>Hello There</xml>
	   case Req("rest" :: "user" :: "onlyjson" :: Nil, "json", GetRequest) => userListHtml
	   case Req("rest" :: "user" :: id :: Nil, _, GetRequest) => userXml(id)
	}
	
}