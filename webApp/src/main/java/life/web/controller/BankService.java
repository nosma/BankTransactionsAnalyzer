package life.web.controller;

import life.database.dao.BankTransactionDao;
import life.database.dao.MidataTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;
import life.database.model.MonthStat;
import life.util.BankTransactionUtil;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class BankService implements BankInterface {

  private MonthStatDao monthStatDao;
  private BankTransactionDao bankTransactionDao;
  private MidataTransactionDao midataTransactionDao;
  private BankTransactionUtil bankTransactionUtil;

  public BankService() {
  }

  @Inject
  public BankService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao, MidataTransactionDao midataTransactionDao) {
    this.monthStatDao = monthStatDao;
    this.bankTransactionDao = bankTransactionDao;
    this.midataTransactionDao = midataTransactionDao;
    bankTransactionUtil = new BankTransactionUtil();
  }

  @Override
  public List<TableObject> getTableObjects() {
    List<BankTransaction> allBankTransactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    return bankTransactionUtil.getTableObjectList(allBankTransactions);
  }

  @Override
  public List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber) &&
          (bankTransaction.getTransactiondate().getYear() == yearNumber) &&
          (bankTransaction.getCost() < 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  @Override
  public List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber) &&
          (bankTransaction.getTransactiondate().getYear() == yearNumber) &&
          (bankTransaction.getCost() > 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  private void saveBankTransactions(List<BankTransaction> bankTransactions) {
    for (BankTransaction bankTransaction : bankTransactions) {
      // todo improve the efficiency of the below
      List<BankTransaction> transactions = bankTransactionDao.findByTransactiondateAndDescriptionAndCost(
          bankTransaction.getTransactiondate(),
          bankTransaction.getDescription(),
          bankTransaction.getCost());
      if (transactions.size() == 0) {
        bankTransactionDao.save(bankTransaction);
      }
    }
  }

  @Override
  public void saveTransactions(List<BankTransaction> bankTransactions) {
    saveBankTransactions(bankTransactions);
    updateMonthStats();
  }


  @Override
  public void saveMidata(List<MidataTransaction> transactions) {
    saveMidataTransactions(transactions);
    updateMonthStats();
  }

  private void updateMonthStats() {
    deleteMonthStat();
    for(BankTransaction bankTransaction : bankTransactionDao.findAll()) {
      setMonthStat(bankTransaction);
    }
  }

  private void saveMidataTransactions(List<MidataTransaction> transactions) {
    for(MidataTransaction transaction : transactions) {
      midataTransactionDao.save(transaction);
      if(bankTransactionDao.findByTransactiondateAndDescriptionAndCost(
          transaction.getDate(),
          transaction.getDescription(),
          transaction.getCost()
      ).size() == 0) {
        bankTransactionDao.save(new BankTransaction(transaction.getDate(), transaction.getDescription(), transaction.getCost()));
      }
    }
  }

  private void deleteMonthStat(){
    monthStatDao.deleteAll();
  }

  private void setMonthStat(BankTransaction bankTransaction) {
    DecimalFormat decimalFormat = new DecimalFormat("#.00");
    double income = 0;
    double expense = 0;
    double profit;

    YearMonth yearMonth = YearMonth.of(bankTransaction.getTransactiondate().getYear(), bankTransaction.getTransactiondate().getMonthValue());
    if (bankTransaction.getCost() > 0) {
      income = bankTransaction.getCost();
    } else {
      expense = bankTransaction.getCost();
    }
    profit = income + expense;

    if (monthStatDao.findAllByOrderByYearMonthDesc().isEmpty()) {
      monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
    } else {
      MonthStat monthStatForUpdate = monthStatDao.findByYearMonth(yearMonth);
      if (monthStatForUpdate == null) {
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


