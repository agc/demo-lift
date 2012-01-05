package org.acidbits.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import scala.xml._
import org.acidbits.comet.ChatServer


object ChatIn {

  /**
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
   */
  def render = SHtml.onSubmit(s => {
    ChatServer ! s
    SetValById("chat_in", "")
  })
}