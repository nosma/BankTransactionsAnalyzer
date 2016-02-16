package com.fragmanos.web.controller;

import java.util.List;

import com.fragmanos.database.model.MonthStat;

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
