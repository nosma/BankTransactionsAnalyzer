package life.file;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtilsImpl {

  private final String[] patterns = new String[]{"yyyy-MM-dd", "M/dd/yy", "M/d/yy", "MM/dd/yy", "MM/dd/yyyy", "dd/MM/yyyy"};

  public LocalDate convertTextToDate(String date) {
    for(String pattern : patterns) {
      try {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
      } catch(DateTimeParseException ignored) {}
    }
    throw new IllegalArgumentException("Not able to parse the date for all patterns given: " + date);
  }

  public LocalDate midataTextToDate(String date, String pattern) {
    try {
      return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    } catch(DateTimeParseException ignored) {
    }
    throw new IllegalArgumentException("Not able to parse the date for all patterns given: " + date);
  }
}
