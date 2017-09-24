package life.file;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  public LocalDate getLocalDate(String date, String pattern) throws Exception {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
  }

}
