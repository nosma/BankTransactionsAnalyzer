package life.file.parser;

import life.database.model.BankTransaction;
import life.file.DateUtilsImpl;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    List<BankTransaction> bankTransactions = new ArrayList<>();

    read(new FileInputStream(file), (rowNumber, cells) ->
      bankTransactions.add(
          new BankTransaction(
            dateUtils.convertTextToDate(cells[0]),
            cells[1],
            Double.parseDouble(cells[2])))
    );
    return bankTransactions;
  }

}
