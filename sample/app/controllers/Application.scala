package controllers

import controllers.stack._
import javax.inject.{Inject, Singleton}
import models._
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) with DBSessionElement with LoggingElement {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def messages = StackAction { implicit req =>
    val messages = Message.findAll
    Ok(views.html.messages(messages))
  }

  def editMessage(id: MessageId) = StackAction { implicit req =>
    val messages = Message.findAll
    Ok(views.html.messages(messages))
  }

}
