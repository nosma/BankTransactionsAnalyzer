package life.web.controller;



import life.database.model.BankTransaction;

import java.util.List;

interface BankInterface {
  List<TableObject> getTableObjects();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);

  void saveBankTransactions(List<BankTransaction> bankTransactions);
}
