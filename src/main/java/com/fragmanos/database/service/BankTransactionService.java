package com.fragmanos.database.service;

import java.util.List;

import com.fragmanos.database.model.BankTransaction;

/**
 * @author fragkakise on 22/09/2015.
 */
public interface BankTransactionService {
  void saveBankTransaction(BankTransaction transaction);
  List<BankTransaction> findAllBankTransactions();
  void deleteBankTransactionByID(int bankTrasnactionID);
  BankTransaction findBankTransactionByID(int bankTrasnactionID);
  void updateBankTransaction(BankTransaction bankTransaction);
}
