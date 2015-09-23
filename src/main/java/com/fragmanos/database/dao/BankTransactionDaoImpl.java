package com.fragmanos.database.dao;

import java.util.List;

import com.fragmanos.database.model.BankTransaction;
import org.springframework.stereotype.Repository;

/**
 * @author fragkakise on 22/09/2015.
 */
@Repository
public class BankTransactionDaoImpl extends AbstractDao implements BankTransactionDao {

  public void saveBankTransaction(BankTransaction transaction) {
    persist(transaction);
  }

  @SuppressWarnings("unchecked")
  public List<BankTransaction> findAllBankTransactions() {
    return selectAll();
  }

  @Override
  public void deleteBankTransactionByID(int bankTrasnactionID) {
    deleteById(bankTrasnactionID);
  }

  @Override
  public void deleteBankTransaction(BankTransaction transaction) {
    delete(transaction);
  }

  @Override
  public BankTransaction findBankTransactionByID(int bankTrasnactionID) {
   return null;
  }

  @Override
  public void updateBankTransaction(BankTransaction bankTransaction) {

  }
}
