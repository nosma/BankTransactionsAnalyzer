package life.file.parser;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import life.database.model.MidataTransaction;
import life.file.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import static life.file.parser.DatePattern.MIDATA_DATE_PATTERN;

public class MidataCsvParser extends CsvParser {

  private static final Logger logger = Logger.getLogger(MidataCsvParser.class);
  private DateUtils dateUtils = new DateUtils();
  private final String datePattern = MIDATA_DATE_PATTERN.getDatePattern();

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
    String balance = line.substring(StringUtils.ordinalIndexOf(line, ",", 4) + 1,
        line.length());
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
    LocalDate midataDate = null;
    try {
      midataDate = dateUtils.getLocalDate(line.substring(0, line.indexOf(",")), datePattern);
    } catch(Exception e) {
      logger.error("Midata Date Format Exception for date '" + line + "' and pattern " + datePattern);
    }
    return midataDate;
  }

}