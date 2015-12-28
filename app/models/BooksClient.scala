package models

import javax.inject._
import play.api.libs.ws._
import play.api.libs.json._
  import pt.org.apec.services.books.common._
  import pt.org.apec.services.books.common.json.JsonFormaters
    import godiva.core.pagination._

  import godiva.json.PlayJsonProtocol
  

/**
 * @author ragb
 */
@Singleton
class BooksClient @Inject() (ws : WSClient) extends JsonFormaters with PlayJsonProtocol {
  val config = play.api.Play.current.configuration
  val baseUrl = config.getString("booksService.client.url") getOrElse (throw new IllegalStateException("books service base url not provided"))
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
  
  
  def authorsUrl = baseUrl + "/authors"
  def categoriesUrl = baseUrl + "/categories"
  def publicationsUrl = baseUrl + "/publications"
  def publicationStatusesUrl = baseUrl + "/publicationStatuses"
  private def mkWs(url : String, queryString: Seq[(String, String)] = Nil) = {
   ws.url(url)
    .withQueryString(queryString:_*)
  }
  
  implicit val paginatedPublicationsREads = paginatedResultReads[PublicationInfo]
  def getCategories = get[Seq[WithPublicationCount[Category]]](categoriesUrl, Seq("counts" -> true.toString))
  def getAuthors = get[Seq[Author]](authorsUrl)
  def searchAuthors(query: String) = get[Seq[Author]](authorsUrl, Seq("q" -> query))
    def getPublications(pagination: PaginationRequest) = {
   val queryString = Seq("pageNumber" -> pagination.page.number.toString, "pageSize" -> pagination.page.size.toString)
   get[PaginatedResult[PublicationInfo]](publicationsUrl, queryString)
  }
  def searchPublications(pagination: PaginationRequest, query: String) = {
   val queryString = Seq("pageNumber" -> pagination.page.number.toString, "pageSize" -> pagination.page.size.toString, "q" -> query)
   get[PaginatedResult[PublicationInfo]](publicationsUrl, queryString)
  }
    def getPublicationStatuses = get[Seq[PublicationStatus]](publicationStatusesUrl)
  
  private def get[T : Reads](url : String, queryString : Seq[(String, String)] = Nil) = mkWs(url, queryString).get.map(unmarshallResponse[T])
  private def unmarshallResponse[T : Reads](r : WSResponse) = r.json.as[T]
}