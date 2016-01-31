package com.fragmanos.web.controller;

import java.util.List;

import com.fragmanos.database.model.MonthStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics/")
public class BankStatisticsController {

  @Autowired
  private BankStatisticsInterface bankStatisticsInterface;

  @RequestMapping(value = "totalIncome")
  public double getTotalIncome() {
    return bankStatisticsInterface.getTotalIncome();
  }

  @RequestMapping(value = "totalExpenses")
  public double getTotalExpenses() {
    return bankStatisticsInterface.getTotalExpenses();
  }

  @RequestMapping("monthly")
  public List<MonthStat> getMonthlyStatistics(){
    return bankStatisticsInterface.getMonthlyStatistics();
  }

  @RequestMapping(value = "monthlyIncome/{yearNumber}/{monthNumber}")
  public double getMonthlyIncome(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return  bankStatisticsInterface.getMonthlyIncome(monthNumber, yearNumber);
  }

  @RequestMapping(value = "monthlyExpenses/{yearNumber}/{monthNumber}")
  public double getMonthlyExpenses(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankStatisticsInterface.getMonthlyExpenses(monthNumber, yearNumber);
  }

  @RequestMapping(value = "yearlyExpenses/{yearNumber}")
  public double getYearlyExpenses(@PathVariable int yearNumber) {
    return bankStatisticsInterface.getYearlyExpenses(yearNumber);
  }

  @RequestMapping(value = "yearlyIncome/{yearNumber}")
  public double getYearlyIncome(@PathVariable int yearNumber) {
    return bankStatisticsInterface.getYearlyIncome(yearNumber);
  }

}
