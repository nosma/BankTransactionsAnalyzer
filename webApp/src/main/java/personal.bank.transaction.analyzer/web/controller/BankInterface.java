package personal.bank.transaction.analyzer.web.controller;



import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);

  List<TagObject> getMonthlyTags(int month, int year);

  double getMonthlyCostPerTag(int month, int year, String tag);
}
