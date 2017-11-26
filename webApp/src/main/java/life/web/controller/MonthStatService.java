package life.web.controller;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MonthStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.YearMonth;

@Service
public class MonthStatService {

  private MonthStatDao monthStatDao;
  private BankTransactionDao bankTransactionDao;

  @Autowired
  public MonthStatService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao) {
    this.monthStatDao = monthStatDao;
    this.bankTransactionDao = bankTransactionDao;
  }

  private void deleteMonthStat() {
    monthStatDao.deleteAll();
  }

  private void setMonthStat(BankTransaction bankTransaction) {
    DecimalFormat decimalFormat = new DecimalFormat("#.00");
    double income = 0;
    double expense = 0;
    double profit;

    YearMonth yearMonth = YearMonth.of(bankTransaction.getTransactiondate().getYear(), bankTransaction.getTransactiondate().getMonthValue());
    if(bankTransaction.getCost() > 0) {
      income = bankTransaction.getCost();
    } else {
      expense = bankTransaction.getCost();
    }
    profit = income + expense;

    if(monthStatDao.findAllByOrderByYearMonthDesc().isEmpty()) {
      monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
    } else {
      MonthStat monthStatForUpdate = monthStatDao.findByYearMonth(yearMonth);
      if(monthStatForUpdate == null) {
        monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
      } else {
        double calculatedIncome = Double.parseDouble(decimalFormat.format(income + monthStatForUpdate.getIncome()));
        double calculatedExpenses = Double.parseDouble(decimalFormat.format(expense + monthStatForUpdate.getExpense()));
        double calculatedProfits = Double.parseDouble(decimalFormat.format(profit + monthStatForUpdate.getProfit()));
        monthStatForUpdate.setIncome(calculatedIncome);
        monthStatForUpdate.setExpense(calculatedExpenses);
        monthStatForUpdate.setProfit(calculatedProfits);
        monthStatDao.save(monthStatForUpdate);
      }
    }
  }

  void updateMonthStats() {
    deleteMonthStat();
    for(BankTransaction bankTransaction : bankTransactionDao.findAll()) {
      setMonthStat(bankTransaction);
    }
  }
}