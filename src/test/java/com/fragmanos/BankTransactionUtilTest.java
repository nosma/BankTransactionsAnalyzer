package com.fragmanos;

import java.util.*;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.util.BankTransactionUtil;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BankTransactionUtilTest {

  @Autowired
  BankTransactionUtil bankTransactionUtil;

  List<BankTransaction> bankTransactionList;
  List<BankTransaction> databaseTransactionList;

  @BeforeMethod
  public void setUp() throws Exception {
    bankTransactionList = new ArrayList<>();
    databaseTransactionList = new ArrayList<>();
    bankTransactionUtil = new BankTransactionUtil();
    setBankTransactionsList();
    setDatabaseTransactionsList();
  }

  @Test
  public void testDuplicationAvoidanceWorksAsExpected() throws Exception {
    Assert.assertEquals(2, bankTransactionUtil.unionOfBankTransactions(bankTransactionList,databaseTransactionList).size());
  }

  private void setDatabaseTransactionsList() {
    databaseTransactionList.add(new BankTransaction(new LocalDate(2016, 3, 11), "Transaction #1", 100.0));
    databaseTransactionList.add(new BankTransaction(new LocalDate(2016, 3, 12), "Transaction #2", 200.0));
  }

  private void setBankTransactionsList() {
    bankTransactionList.add(new BankTransaction(new LocalDate(2016, 3, 11), "Transaction #1", 100.0));
    bankTransactionList.add(new BankTransaction(new LocalDate(2016, 3, 11), "Transaction #1", 100.0));
    bankTransactionList.add(new BankTransaction(new LocalDate(2016, 3, 12), "Transaction #2", 200.0));
  }

}
