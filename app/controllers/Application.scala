package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pt.org.apec.services.books.common._
import models._
import godiva.core.pagination._
import play.api.i18n._
import scalaz.{Inject => _, _}
import Scalaz._
import scalaz.OptionT._

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

  def category(slug: String, page: Long) = Action.async { implicit request =>
        val globalData = GlobalData(None, MainForms.searchForm)
    val pagination = PaginationRequest(Page(page, 10), true)

    val result = for {
      category <- OptionT(booksClient.getCategory(slug))
      publications <- OptionT(booksClient.getPublications(pagination, category.guid.some, None).map(_.some))
    } yield(category, publications)
   result.run.map   { 
      _.map {case (c, p) =>
        Ok(views.html.category(ListingPageData(c, p), globalData))
      } getOrElse(NotFound)
    }
  }
  
    def author(slug: String, page: Long) = Action.async { implicit request =>
        val globalData = GlobalData(None, MainForms.searchForm)
    val pagination = PaginationRequest(Page(page, 10), true)

    val result = for {
      author <- OptionT(booksClient.getAuthor(slug))
      publications <- OptionT(booksClient.getPublications(pagination, None, author.guid.some).map(_.some))
    } yield(author, publications)
   result.run.map   { 
      _.map {case (a, p) =>
        Ok(views.html.author(ListingPageData(a, p), globalData))
      } getOrElse(NotFound)
    }
  }

  def catalog = Action.async { implicit request =>
    val globalData = GlobalData(None, MainForms.searchForm)
    for {
      ((authors, categories), statuses) <- booksClient.getAuthors zip booksClient.getCategories zip booksClient.getPublicationStatuses
    } yield(Ok(views.html.catalog(CatalogData(categories, authors, statuses), globalData)))
  }
  
  def publication(slug: String) = Action.async { implicit request =>
        val globalData = GlobalData(None, MainForms.searchForm)

    booksClient.getPublication(slug).map {
      _.map{ p =>
   Ok(views.html.publication(p, globalData))     
    } getOrElse(NotFound)
  }
  }
}
