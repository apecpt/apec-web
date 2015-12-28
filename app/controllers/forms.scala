package controllers

import play.api.data._
import play.api.data.Forms._

object MainForms {
  val searchForm = Form(
      single("q" -> nonEmptyText(maxLength=100)))
}