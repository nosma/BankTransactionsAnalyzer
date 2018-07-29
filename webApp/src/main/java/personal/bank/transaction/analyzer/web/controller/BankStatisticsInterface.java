package personal.bank.transaction.analyzer.web.controller;

import personal.bank.transaction.analyzer.database.model.MonthStat;

import java.util.List;

public interface BankStatisticsInterface {

  double getTotalIncome();

  double getTotalExpenses();

  double getMonthlyIncome(int monthNumber, int yearNumber);

  double getMonthlyExpenses(int monthNumber, int yearNumber);

  double getYearlyExpenses(int yearNumber);

  double getYearlyIncome(int yearNumber);

  List<MonthStat> getMonthlyStatistics();

  double getInitialBalance();

  double getMedianMonthlyExpense();

  double getMedianMonthlyIncome();
}
