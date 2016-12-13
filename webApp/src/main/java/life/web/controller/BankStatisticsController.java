package life.web.controller;


import life.database.model.MonthStat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/statistics/")
public class BankStatisticsController {

  @Inject
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

  @RequestMapping(value = "initialBalance")
  public double getInitialBalance(){
    return bankStatisticsInterface.getInitialBalance();
  }

  @RequestMapping(value = "medianMonthlyExpense")
  public double getMedianMonthlyExpense(){
    return bankStatisticsInterface.getMedianMonthlyExpense();
  }

  @RequestMapping(value = "medianMonthlyIncome")
  public double getMedianMonthlyIncome(){
    return bankStatisticsInterface.getMedianMonthlyIncome();
  }

}
