package com.fragmanos.database.service;

import java.util.List;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.model.BankTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fragkakise on 22/09/2015.
 */
@Service("bankTransactionService")
@Transactional
public class BankTransactionServiceImpl implements BankTransactionService {

  @Autowired
  private BankTransactionDao dao;

  @Override
  public void saveBankTransaction(BankTransaction transaction) {
    dao.saveBankTransaction(transaction);
  }

  @Override
  public List<BankTransaction> findAllBankTransactions() {
    return dao.findAllBankTransactions();
  }

  @Override
  public void deleteBankTransactionByID(int bankTrasnactionID) {
    dao.deleteBankTransactionByID(bankTrasnactionID);
  }

  @Override
  public BankTransaction findBankTransactionByID(int bankTrasnactionID) {
    return dao.findBankTransactionByID(bankTrasnactionID);
  }

  @Override
  public void updateBankTransaction(BankTransaction bankTransaction) {
    dao.updateBankTransaction(bankTransaction);
  }
}
