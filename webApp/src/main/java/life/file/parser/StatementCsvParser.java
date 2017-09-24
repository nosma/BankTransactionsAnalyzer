package life.file.parser;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import life.database.model.BankTransaction;
import life.file.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import static life.file.parser.DatePattern.STATEMENT_DATE_PATTERN;

@Component
@Transactional
public class StatementCsvParser extends CsvParser {

  private static final Logger logger = Logger.getLogger(StatementCsvParser.class);
  private DateUtils dateUtils = new DateUtils();
  private final String statementDatePattern = STATEMENT_DATE_PATTERN.getDatePattern();

  /**
   * Statement parser expect to have 3 headers
   * date : DD/MM/YYYY
   * description [a-zA-Z0-9]
   * amount ##.#
   *
   * @param file read statement csv file
   * @return BankTransaction list
   */
  @Override
  public List<BankTransaction> parse(File file) throws IOException, ParseException, java.text.ParseException {
    List<BankTransaction> transactionList = new ArrayList<>();
    FileInputStream fileInputStream = new FileInputStream(file);
    try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {
      String line;
      while((line = br.readLine()) != null){
        if (isStatementLine(line)){
          transactionList.add(new BankTransaction(
              getTransactionDate(line),
              getDescription(line),
              getCost(line)
          ));
        }
      }
    }
    return transactionList;
  }

  private Double getCost(String line) {
    return Double.valueOf(line.substring(StringUtils.ordinalIndexOf(line, ",", 2)+1,
        line.length()));
  }

  private String getDescription(String line) {
    return line.substring(StringUtils.ordinalIndexOf(line, ",", 1) + 1,
        StringUtils.ordinalIndexOf(line, ",", 2));
  }

  private LocalDate getTransactionDate(String line) {
    LocalDate statementDate = null;
    try {
      statementDate = dateUtils.getLocalDate(line.substring(0, line.indexOf(",")), statementDatePattern);
    } catch(Exception e) {
      logger.error("Statement Date Format Exception for date '" + line + "' and pattern " + statementDatePattern);
    }
    return statementDate;
  }

  private boolean isStatementLine(String line) {
    return StringUtils.countMatches(line, ",") == 2;
  }

}
