package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pt.org.apec.services.books.common._
import models.BooksClient
import godiva.core.pagination._

class Application @Inject() (booksClient : BooksClient) extends Controller {

  def index = Action.async {
    val pagination = PaginationRequest(Page(1,10), true)
    for {
      ((categories, authors), publications) <- booksClient.getCategories zip booksClient.getAuthors zip booksClient.getPublications(pagination)
    } yield {
    Ok(views.html.index(categories, authors, publications.elements))
    }
  }

  def search(page: Long, query: String) = Action.async { implicit request =>
    val pagination = PaginationRequest(Page(page, 10), true)
    for {
      publications <- booksClient.searchPublications(pagination, query)
    } yield(Ok(views.html.search(publications, query)))
  }
  
}
