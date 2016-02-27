package com.fragmanos.web.controller;

import com.fragmanos.database.model.BankTransaction;

import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  void populateDatabase(List<BankTransaction> bankTransactionList);
}
