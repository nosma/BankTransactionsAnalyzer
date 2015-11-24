package com.fragmanos.file;

import com.fragmanos.database.model.BankTransaction;
import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author macuser on 8/23/15.
 */
public class CSVReader {


    public List<BankTransaction> readCSV(String csvFile) throws ParseException, IOException {
    List<BankTransaction> bankTransactionList = new ArrayList<BankTransaction>();
        BufferedReader bufferedReader = null;
        String csvLine;
        String splitByCharacter = ",";

        try {
            bufferedReader = new BufferedReader(new FileReader(csvFile));
            while ((csvLine = bufferedReader.readLine()) != null) {
                BankTransaction bankTransaction = bankCsvDataConvertion(getDateFromLine(csvLine, splitByCharacter),
                                                                         getDescriptionFromLine(csvLine, splitByCharacter),
                                                                         getCostFromLine(csvLine, splitByCharacter));
                bankTransactionList.add(bankTransaction);
//                printTransactions(csvLine, splitByCharacter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bankTransactionList;
    }

    private void printTransactions(String csvLine, String splitByCharacter) {
        StringBuilder transaction = new StringBuilder("");
        transaction.append("Incoming Date: ").
                        append(getDateFromLine(csvLine, splitByCharacter))
                    .append("Incoming Cause: ").
                        append(getDescriptionFromLine(csvLine, splitByCharacter))
                    .append("Incoming Cost: ").
                        append(getCostFromLine(csvLine, splitByCharacter));
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

    private BankTransaction bankCsvDataConvertion(String date, String cause, String cost) throws ParseException {
        DateUtilsImpl dateUtils = new DateUtilsImpl();
        LocalDate transactionDate = dateUtils.convertTextToDate(date);
        double value = Double.parseDouble(cost);
        return new BankTransaction(transactionDate, cause, value);
    }

}
