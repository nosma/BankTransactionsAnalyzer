package com.fragmanos.web.controller;

import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  void populateDatabase();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);
}
