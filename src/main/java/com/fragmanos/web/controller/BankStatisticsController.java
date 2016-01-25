package com.fragmanos.web.controller;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.database.model.MonthStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/")
public class BankStatisticsController {

  private final BankStatisticsService bankStatisticsService;

  @Autowired
  public BankStatisticsController(BankStatisticsService bankStatisticsService) {
    this.bankStatisticsService = bankStatisticsService;
  }

  @RequestMapping(value = "totalIncome")
  public double getTotalIncome() {
    return bankStatisticsService.getTotalIncome();
  }

  @RequestMapping(value = "totalExpenses")
  public double getTotalExpenses() {
    return bankStatisticsService.getTotalExpenses();
  }

  @RequestMapping("monthly")
  public List<MonthStat> getMonthlyStatistics(){
    return bankStatisticsService.getMonthlyStatistics();
  }

  @RequestMapping(value = "monthlyIncome/{yearNumber}/{monthNumber}")
  public double getMonthlyIncome(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return  bankStatisticsService.getMonthlyIncome(monthNumber, yearNumber);
  }

  @RequestMapping(value = "monthlyExpenses/{yearNumber}/{monthNumber}")
  public double getMonthlyExpenses(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankStatisticsService.getMonthlyExpenses(monthNumber, yearNumber);
  }

  @RequestMapping(value = "yearlyExpenses/{yearNumber}")
  public double getYearlyExpenses(@PathVariable int yearNumber) {
    return bankStatisticsService.getYearlyExpenses(yearNumber);
  }

  @RequestMapping(value = "yearlyIncome/{yearNumber}")
  public double getYearlyIncome(@PathVariable int yearNumber) {
    return bankStatisticsService.getYearlyIncome(yearNumber);
  }

}
