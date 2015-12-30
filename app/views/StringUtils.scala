package views

import java.text.Collator
import java.util.Locale

object StringUtils {
  def catalogCharacter(str: String) = {
    str.toUpperCase.toCharArray.find {_.isLetter}
    
  }
}