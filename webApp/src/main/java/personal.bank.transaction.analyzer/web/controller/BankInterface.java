package personal.bank.transaction.analyzer.web.controller;



import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);

  List<TagObject> getMonthlyTagsGroupedByTag(int month, int year);
}
