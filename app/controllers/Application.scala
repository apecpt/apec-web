package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pt.org.apec.services.books.common._
import models.BooksClient

class Application @Inject() (booksClient : BooksClient) extends Controller {

  def index = Action.async {
    for {
      ((categories, authors), publications) <- booksClient.getCategories zip booksClient.getAuthors zip booksClient.getPublications
    } yield {
    Ok(views.html.index(categories, authors, publications))
    }
  }

}
