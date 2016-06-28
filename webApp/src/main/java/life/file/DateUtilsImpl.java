package life.file;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtilsImpl {
    private final String[] patterns = new String[]{"yyyy-MM-dd", "M/dd/yy", "M/d/yy", "MM/dd/yy", "MM/dd/yyyy", "dd/MM/yyyy"};

    public LocalDate convertTextToDate(String date) throws ParseException {
        for (int i = 0; i < patterns.length; i++) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(patterns[i]));
            } catch (DateTimeParseException exception) {

            }
        }
        throw new IllegalArgumentException("Not able to parse the date for all patterns given: " + date);
    }
}
