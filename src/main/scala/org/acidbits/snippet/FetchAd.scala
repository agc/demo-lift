package org.acidbits.snippet
import net.liftweb._
import java.util.Random
import util._
import Helpers._

object FetchAd {
  def render = {
    // sleep for 1 second
    Thread.sleep(1 second)
 
    // send the result back
    ".ad" #> Thread.currentThread.getName 
  }
}