package com.fragmanos.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVReader;
import com.fragmanos.web.controller.TableObject;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionUtil {

  List<BankTransaction> totalBankTransactions = new ArrayList<BankTransaction>();
  private static final Logger log = LoggerFactory.getLogger(BankTransactionUtil.class);
  CSVReader csvReader = new CSVReader();
  DirectoryReader directoryReader = new DirectoryReader();

  public List<BankTransaction> getBankTransactionsFromDirectory(String inputDirectory) throws ParseException, IOException {
    for (String file : directoryReader.csvScanner(inputDirectory)) {
      List<BankTransaction> fileBankTransactionList = csvReader.readCSV(inputDirectory + file);
      totalBankTransactions = filterUniqueBankTransactions(totalBankTransactions, fileBankTransactionList);
    }
    return totalBankTransactions;
  }

  private List<BankTransaction> filterUniqueBankTransactions(List<BankTransaction> totalBankTransactionList, List<BankTransaction> fileBankTransactionList) {
    if(!totalBankTransactionList.isEmpty()) {
      List<BankTransaction> localBankTransactionList = new ArrayList<BankTransaction>(totalBankTransactionList);
      localBankTransactionList.retainAll(fileBankTransactionList);
      for(BankTransaction bankTransaction : localBankTransactionList) {
        totalBankTransactionList.remove(bankTransaction);
      }
      totalBankTransactionList.addAll(fileBankTransactionList);
      return totalBankTransactionList;
    } else {
      totalBankTransactionList.addAll(fileBankTransactionList);
      return totalBankTransactionList;
    }
  }

   public List<TableObject> getTableObjectList(List<BankTransaction> bankTransactionList) {
        List<TableObject> tableObjectList = new ArrayList<TableObject>();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        for (BankTransaction bankTransaction : bankTransactionList) {
            tableObjectList.add(new TableObject(
                    dtf.print(bankTransaction.getTransactiondate()),
                    bankTransaction.getDescription(),
                    bankTransaction.getCost()
            ));
        }
        return tableObjectList;
    }

}
