package models

import pt.org.apec.services.books.common._

case class PageData(categories: Seq[WithPublicationCount[Category]] = Seq.empty)