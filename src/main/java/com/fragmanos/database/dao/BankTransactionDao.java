package com.fragmanos.database.dao;

import java.util.List;

import com.fragmanos.database.model.BankTransaction;

/**
 * @author fragkakise on 22/09/2015.
 */
public interface BankTransactionDao {
  void saveBankTransaction(BankTransaction transaction);
  List<BankTransaction> findAllBankTransactions();
  void deleteBankTransactionByID(int bankTrasnactionID);
  void deleteBankTransaction(BankTransaction transaction);
  BankTransaction findBankTransactionByID(int bankTrasnactionID);
  void updateBankTransaction(BankTransaction bankTransaction);
}
