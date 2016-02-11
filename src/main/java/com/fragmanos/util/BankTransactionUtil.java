package com.fragmanos.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankTransactionUtil {

  List<BankTransaction> totalBankTransactions = new ArrayList<BankTransaction>();
  private static final Logger log = LoggerFactory.getLogger(BankTransactionUtil.class);
  CSVParser csvParser = new CSVParser();
  DirectoryReader directoryReader = new DirectoryReader();

  public List<BankTransaction> getBankTransactionsFromDirectory(String inputDirectory) throws ParseException, IOException {
    for (String file : directoryReader.csvScanner(inputDirectory)) {
      List<BankTransaction> fileBankTransactionList = csvParser.getTransactions(inputDirectory + file);
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
}
