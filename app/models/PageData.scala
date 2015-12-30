package models

import play.api.data._
import pt.org.apec.services.books.common._
import pt.org.apec.services.users.common._
import godiva.core.pagination._

case class UserData(username: String, userId: UserId,profile: UserProfile)

case class GlobalData(user: Option[UserData], searchForm: Form[String])

case class IndexData(categories: Seq[WithPublicationCount[Category]], publications: Seq[PublicationInfo])
case class CatalogData(categories: Seq[WithPublicationCount[Category]] = Seq.empty, authors: Seq[Author] = Seq.empty, statuses: Seq[PublicationStatus] = Seq.empty)
case class ListingPageData[T](element: T, publications: PaginatedResult[PublicationInfo])
