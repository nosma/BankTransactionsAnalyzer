package com.fragmanos.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVReader;

/**
 * @author fragkakise on 28/10/2015.
 */
public class TransactionController {

  CSVReader csvReader = new CSVReader();
  DirectoryReader directoryReader = new DirectoryReader();

  public List<BankTransaction> getBankTransactionsFromDirectory(String inputDirectory) throws ParseException, IOException {
    List<BankTransaction> bankTransactions = new ArrayList<BankTransaction>();
    for (String file : directoryReader.csvScanner(inputDirectory)){
      List<BankTransaction> bankTransactionList = csvReader.readCSV(inputDirectory + file);
      bankTransactions.addAll(bankTransactionList);
    }
    return bankTransactions;
  }

}
