package pl.itigo.snippet
import net.liftweb._
import util._
import Helpers._


object ShortTime {
  def render = {
    // capture the start time
    val start = now
 
    // sleep for up to 15 seconds
    Thread.sleep(3 seconds)
 
    // send the result back
    "#start" #> start.toString &
    "#end" #> now.toString
   }
}