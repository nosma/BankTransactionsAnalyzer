package life.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import life.database.model.BankTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVParser {

    private static final Logger log = LoggerFactory.getLogger(CSVParser.class);
    private DateUtilsImpl dateUtils = new DateUtilsImpl();

    public List<BankTransaction> getTransactions(String csvFile) throws ParseException, IOException {
        List<BankTransaction> bankTransactionList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        String csvLine;
        String splitByCharacter = ",";

        try {
            bufferedReader = new BufferedReader(new FileReader(csvFile));
            while ((csvLine = bufferedReader.readLine()) != null) {
                bankTransactionList.add(bankCsvDataConversion(
                  getDateFromLine(csvLine, splitByCharacter),
                  getDescriptionFromLine(csvLine, splitByCharacter),
                  getCostFromLine(csvLine, splitByCharacter)));
            }
        } catch (IOException e) {
            log.error("Error while reading line from file: " + e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("Error while trying to close the Buffer reader of teh file: " + e);
                }
            }
        }
        return bankTransactionList;
    }

    private static String getCostFromLine(String csvLine, String splitByCharacter) {
        return csvLine.substring(csvLine.lastIndexOf(splitByCharacter) + 1, csvLine.length());
    }

    private static String getDescriptionFromLine(String csvLine, String splitByCharacter) {
        return csvLine.substring(csvLine.indexOf(splitByCharacter) + 1, csvLine.lastIndexOf(splitByCharacter));
    }

    private static String getDateFromLine(String csvLine, String splitByCharacter) {
        return csvLine.substring(0, csvLine.indexOf(splitByCharacter));
    }

    private BankTransaction bankCsvDataConversion(String date, String description, String cost) throws ParseException {
        LocalDate transactionDate = dateUtils.convertTextToDate(date);
        double value = Double.parseDouble(cost);
        return new BankTransaction(transactionDate, description, value);
    }

    public List<BankTransaction> getMidata(String midataFilePath) {
        List<BankTransaction> bankTransactionList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        String csvLine;
        String splitByCharacter = ",";

        try {
            bufferedReader = new BufferedReader(new FileReader(midataFilePath));
            bufferedReader.readLine(); // skip the headings
            while ((csvLine = bufferedReader.readLine()) != null) {
                bankTransactionList.add(new BankTransaction(
                    getDateFromMidata(csvLine, splitByCharacter),
                    getDescriptionFromMidata(csvLine, splitByCharacter),
                    getCostFromMidata(csvLine, splitByCharacter)));
            }
        } catch (IOException e) {
            log.error("Error while reading line from file: " + e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("Error while trying to close the Buffer reader of teh file: " + e);
                }
            }
        }
        return bankTransactionList;
    }

    private double getCostFromMidata(String csvLine, String splitByCharacter) {
        String costText = csvLine.substring(StringUtils.ordinalIndexOf(csvLine, splitByCharacter, 3) + 1, StringUtils.ordinalIndexOf(csvLine, splitByCharacter, 4));
        return convertMidataCostToAmount(costText);
    }

    private double convertMidataCostToAmount(String costText) {
        return Double.parseDouble(costText.replace("£", ""));
    }

    private String getDescriptionFromMidata(String csvLine, String splitByCharacter) {
        return csvLine.substring(StringUtils.ordinalIndexOf(csvLine, splitByCharacter, 2) + 1, StringUtils.ordinalIndexOf(csvLine, splitByCharacter, 3));
    }

    private LocalDate getDateFromMidata(String csvLine, String splitByCharacter)  {
        LocalDate localDate;
        try{
            localDate = dateUtils.convertTextToDate(csvLine.substring(0, csvLine.indexOf(splitByCharacter)));
        } catch (Exception e) {
            throw new RuntimeException("getDateFromMidata Exception " + e);
        }

        return localDate;
    }
}
