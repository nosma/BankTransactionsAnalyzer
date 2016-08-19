package life.file;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtilsImpl {

  private static final Logger log = LoggerFactory.getLogger(DateUtilsImpl.class);

  private final String[] patterns = new String[]{"yyyy-MM-dd", "M/dd/yy", "M/d/yy", "MM/dd/yy", "MM/dd/yyyy", "dd/MM/yyyy"};

  public LocalDate convertTextToDate(String date) throws ParseException {
    for(String pattern : patterns) {
      try {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
      } catch(DateTimeParseException ignored) {}
    }
    throw new IllegalArgumentException("Not able to parse the date for all patterns given: " + date);
  }
}
