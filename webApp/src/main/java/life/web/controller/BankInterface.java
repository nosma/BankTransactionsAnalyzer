package life.web.controller;



import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;

import java.util.List;

interface BankInterface {
  List<TableObject> getTableObjects();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);

  void saveTransactions(List<BankTransaction> bankTransactions);

  void saveMidata(List<MidataTransaction> bankTransactions);
}
