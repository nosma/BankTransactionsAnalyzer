package com.fragmanos.controller;

import java.util.List;

import com.fragmanos.database.model.BankTransaction;

/**
 * @author fragkakise on 30/11/2015.
 */
public class TransactionsUtil {

  List<BankTransaction> totalBankTransactions;

  public TransactionsUtil(List<BankTransaction> totalBankTransactions) {
    this.totalBankTransactions = totalBankTransactions;
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
