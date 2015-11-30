package com.fragmanos.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fragkakise on 28/10/2015.
 */
public class TransactionController {

  List<BankTransaction> totalBankTransactions = new ArrayList<BankTransaction>();
  private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
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

  public double getTotalIncome() {
    double totalIncome = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      totalIncome += (bankTransaction.getCost()>0?bankTransaction.getCost() : 0);
    }
    return totalIncome;
  }

  public double getTotalExpenses() {
    double totalExpenses = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      totalExpenses += (bankTransaction.getCost()<0?bankTransaction.getCost() : 0);
    }
    return totalExpenses;
  }

  public double getMonthlyIncome(int monthNumber, int yearNumber) {
    double monthlyIncome = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      if((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
           (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
        monthlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
      }
    }
    return monthlyIncome;
  }

  public double getMonthlyExpenses(int monthNumber, int yearNumber) {
    double monthlyExpenses = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      if((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
           (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
        monthlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
      }
    }
    return monthlyExpenses;
  }

  public double getYearlyExpenses(int yearNumber) {
    double yearlyExpenses = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      if(bankTransaction.getTransactiondate().getYear() == yearNumber) {
        yearlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
      }
    }
    return yearlyExpenses;
  }

  public double getYearlyIncome(int yearNumber) {
    double yearlyIncome = 0;
    for(BankTransaction bankTransaction : totalBankTransactions) {
      if(bankTransaction.getTransactiondate().getYear() == yearNumber) {
        yearlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
      }
    }
    return yearlyIncome;
  }
}
