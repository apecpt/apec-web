package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pt.org.apec.services.books.common._
import models._
import godiva.core.pagination._
import play.api.i18n._

class Application @Inject() (booksClient : BooksClient, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action.async {
    val pagination = PaginationRequest(Page(1,10), true)
    for {
      ((categories, authors), publications) <- booksClient.getCategories zip booksClient.getAuthors zip booksClient.getPublications(pagination)
    } yield {
    Ok(views.html.index(GlobalData(None, MainForms.searchForm),IndexData(categories,  publications.elements)))
    }
  }

  def search(page: Long, query: String) = Action.async { implicit request =>
    val globalData = GlobalData(None, MainForms.searchForm.bind(Map("q" -> query)))
    val pagination = PaginationRequest(Page(page, 10), true)
    for {
      publications <- booksClient.searchPublications(pagination, query)
    } yield(Ok(views.html.search(publications, query, globalData)))
  }
  
  def catalog = Action.async { implicit request =>
    val globalData = GlobalData(None, MainForms.searchForm)
    for {
      ((authors, categories), statuses) <- booksClient.getAuthors zip booksClient.getCategories zip booksClient.getPublicationStatuses
    } yield(Ok(views.html.catalog(CatalogData(categories, authors, statuses), globalData)))
  }
}
