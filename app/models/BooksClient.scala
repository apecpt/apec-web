package models

import javax.inject._
import play.api.libs.ws._
import play.api.libs.json._

/**
 * @author ragb
 */
@Singleton
class BooksClient @Inject() (ws : WSClient) {
  val config = play.api.Play.current.configuration
  val baseUrl = config.getString("booksService.client.url") getOrElse (throw new IllegalStateException("books service base url not provided"))
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  import pt.org.apec.services.books.common._
  import pt.org.apec.services.books.common.json.JsonFormaters._
  
  
  
  
  def authorsUrl = baseUrl + "/authors"
  def categoriesUrl = baseUrl + "/categories"
  def publicationsUrl = baseUrl + "/publications"
  def publicationStatusesUrl = baseUrl + "/publicationStatuses"
  private def mkWs(url : String) = ws.url(url)
  
  def getCategories = get[Seq[Category]](categoriesUrl)
  def getAuthors = get[Seq[Author]](authorsUrl)
    def getPublications = get[Seq[PublicationInfo]](publicationsUrl)
    def getPublicationStatuses = get[Seq[PublicationStatus]](publicationStatusesUrl)
  
  private def get[T : Reads](url : String) = mkWs(url).get.map(unmarshallResponse[T])
  private def unmarshallResponse[T : Reads](r : WSResponse) = r.json.as[T]
}