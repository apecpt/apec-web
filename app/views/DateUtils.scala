package views
import org.joda.time._
import org.joda.time.format.DateTimeFormat


object DateUtils {
  val daysFormat = DateTimeFormat.mediumDate()
  def dateDay(time: DateTime) : String = daysFormat.print(time)
  
}