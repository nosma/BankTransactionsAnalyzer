package personal.bank.transaction.analyzer.parser;

import org.apache.commons.lang3.StringUtils;
import personal.bank.transaction.analyzer.database.model.MidataTransaction;
import personal.bank.transaction.analyzer.util.DateConverter;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MidataCsvParser extends CsvParser {

  /**
   * Midata parser expects to have 5 headers
   * date: DD/MM/YYYY
   * type: [a-zA-Z]
   * description: [a-zA-Z0-9]
   * debit/credit: ##.#
   * balance: ##.#
   *
   * @param file read midata file
   * @return MidataTransaction list
   * @throws IOException
   * @throws ParseException
   */
  @Override
  public List<MidataTransaction> parse(File file) throws IOException, ParseException {
    List<MidataTransaction> midataList = new ArrayList<>();
    FileInputStream fileInputStream = new FileInputStream(file);
    try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {
      String line;
      br.readLine(); // escape first line with headers
      while ((line = br.readLine()) != null) {
        if (isMidataLine(line)) {
          midataList.add(new MidataTransaction(
              getDate(line),
              getType(line),
              getDescription(line),
              getCost(line),
              getBalance(line)
          ));
        }
      }
    }
    return midataList;
  }

  private boolean isMidataLine(String line) {
    return StringUtils.countMatches(line, ",") == 4;
  }

  private double getBalance(String line) {
    String balance = line.substring(StringUtils.ordinalIndexOf(line, ",", 4) + 1);
    return Double.valueOf(balance.replace("£", ""));
  }

  private double getCost(String line) {
    String cost = line.substring(StringUtils.ordinalIndexOf(line, ",", 3) + 1,
        StringUtils.ordinalIndexOf(line, ",", 4));
    return Double.valueOf(cost.replace("£", ""));
  }

  private String getDescription(String line) {
    return line.substring(StringUtils.ordinalIndexOf(line, ",", 2) + 1,
        StringUtils.ordinalIndexOf(line, ",", 3));
  }

  private String getType(String line) {
    return line.substring(StringUtils.ordinalIndexOf(line, ",", 1) + 1,
        StringUtils.ordinalIndexOf(line, ",", 2));
  }

  private LocalDate getDate(String line) {
    return DateConverter.getMidataDate(line.substring(0, line.indexOf(',')));
  }

}