package life.web.controller;

import life.database.dao.BankTransactionDao;
import life.database.dao.MidataTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;
import life.database.model.MonthStat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.List;

@Service
public class StorageWriterService implements StorageWriter {

  private Logger log = Logger.getLogger(StorageWriterService.class.getName());

  private BankTransactionDao bankTransactionDao;
  private MidataTransactionDao midataTransactionDao;
  private MonthStatDao monthStatDao;

  @Autowired
  public StorageWriterService(BankTransactionDao bankTransactionDao, MidataTransactionDao midataTransactionDao, MonthStatDao monthStatDao) {
    this.bankTransactionDao = bankTransactionDao;
    this.midataTransactionDao = midataTransactionDao;
    this.monthStatDao = monthStatDao;
  }

  @Override
  public void saveTransactions(List<BankTransaction> bankTransactions) {
    saveBankTransactions(bankTransactions);
    updateMonthStats();
  }

  @Override
  public void saveMidata(List<MidataTransaction> midataTransactions) {
    saveMidataTransactions(midataTransactions);
    saveMidataBankTransactions(midataTransactions);
    updateMonthStats();
  }

  private void updateMonthStats() {
    deleteMonthStat();
    for(BankTransaction bankTransaction : bankTransactionDao.findAll()) {
      setMonthStat(bankTransaction);
    }
  }

  private void saveBankTransactions(List<BankTransaction> bankTransactions) {
    for(BankTransaction transaction : bankTransactions) {
      try {
        bankTransactionDao.save(transaction);
      } catch(Exception e) {
        log.error("Duplicate statement transaction: " + transaction.toString());
      }
    }
  }

  private void saveMidataBankTransactions(List<MidataTransaction> midataTransactions) {
    for(MidataTransaction midataTransaction : midataTransactions) {
      try {
        bankTransactionDao.save(new BankTransaction(
            midataTransaction.getDate(),
            midataTransaction.getDescription(),
            midataTransaction.getCost()
        ));
      } catch(Exception e) {
        log.error("Duplicate Midata statement transaction: " + midataTransaction.toString());
      }
    }
  }

  private void saveMidataTransactions(List<MidataTransaction> midataTransactions) {
    for(MidataTransaction midataTransaction : midataTransactions) {
      try {
        midataTransactionDao.save(midataTransaction);
      } catch(Exception e) {
        log.error("Duplicate Midata transaction: " + midataTransaction.toString());
      }
    }
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

}
