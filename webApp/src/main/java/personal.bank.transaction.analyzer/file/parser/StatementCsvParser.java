package personal.bank.transaction.analyzer.file.parser;

import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.file.DateUtilsImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class StatementCsvParser extends CsvParser {

  private DateUtilsImpl dateUtils = new DateUtilsImpl();

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
    return dateUtils.getStatementDate(line.substring(0, line.indexOf(",")));
  }

  private boolean isStatementLine(String line) {
    return StringUtils.countMatches(line, ",") == 2;
  }

}
