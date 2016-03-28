package life.web.controller;



import life.database.model.BankTransaction;

import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  void populateDatabase();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);
  
  void populateDatabase(List<BankTransaction> bankTransactionList);

  List<BankTransaction> getDbBankTransactions();
}
