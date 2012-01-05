package pl.itigo.snippet
import net.liftweb._
import util._
import Helpers._

object FetchAd {
  def render = {
    // sleep for 1/2 second
    Thread.sleep(500 millis)
 
    // send the result back
    ".ad" #> Thread.currentThread.getName
  }
}