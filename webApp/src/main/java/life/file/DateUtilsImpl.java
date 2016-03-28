package life.file;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;

public class DateUtilsImpl {
    public LocalDate convertTextToDate(String date) throws ParseException {
        LocalDate returnedDate;
        if (date.contains("-")) {
            returnedDate = getDateFromStringWithDashes(date);
        } else if (date.contains("/")) {
            returnedDate = getDateFromStringWithSlashes(date);
        } else {
            throw new RuntimeException("Unexpected Date seperation character");
        }
        return returnedDate;
    }

    private LocalDate getDateFromStringWithDashes(String date) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dtf.parseLocalDate(date);
    }

    private LocalDate getDateFromStringWithSlashes(String date) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yy");
        return dtf.parseLocalDate(date);
    }

    public LocalDate convertMidataTextToDate(String midataDate) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        return dtf.parseLocalDate(midataDate);
    }
}
