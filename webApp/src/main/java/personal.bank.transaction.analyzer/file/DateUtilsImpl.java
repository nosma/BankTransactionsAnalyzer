package personal.bank.transaction.analyzer.file;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateUtilsImpl implements DateUtils {

  private static final String MIDATA_DATE_PATTERN = "dd/MM/yyyy";
  private static final String STATEMENT_DATE_PATTERN = "yyyy-MM-dd";
  private static final Logger logger = Logger.getLogger(DateUtilsImpl.class);

  public DateUtilsImpl() {
  }

  public LocalDate getStatementDate(String date) {
    LocalDate statementDate = null;
    try {
      statementDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(STATEMENT_DATE_PATTERN));
    } catch (DateTimeParseException exception) {
      logger.error("Not able to parse the statement date " + date + " for pattern given: " + STATEMENT_DATE_PATTERN + exception);
    }
    return statementDate;
  }

  public LocalDate getMidataDate(String date) {
    LocalDate midataDate = null;
    try {
      midataDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(MIDATA_DATE_PATTERN));
    } catch(DateTimeParseException exception) {
      logger.error("Not able to parse the midata date " + date + " for pattern given: " + MIDATA_DATE_PATTERN + exception);
    }
    return midataDate;
  }

}
