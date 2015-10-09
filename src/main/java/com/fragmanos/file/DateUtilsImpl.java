package com.fragmanos.file;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;

/**
 * @author macuser on 8/23/15.
 */
public class DateUtilsImpl {
    public LocalDate convertTextToDate(String s) throws ParseException {
        LocalDate returnedDate = null;
        if (s.contains("-")) {
            returnedDate = getDateFromStringWithDashes(s);
        } else if (s.contains("/")) {
            returnedDate = getDateFromStringWithSlashes(s);
        }
        return returnedDate;
    }

    private LocalDate getDateFromStringWithDashes(String s) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dtf.parseLocalDate(s);
    }

    private LocalDate getDateFromStringWithSlashes(String s) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yy");
        return dtf.parseLocalDate(s);
    }
}
