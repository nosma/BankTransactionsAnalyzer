package com.fragmanos.web.controller;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.database.model.MonthStat;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.util.BankTransactionUtil;
import org.joda.time.YearMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankService implements BankInterface {

  private static final Logger log = LoggerFactory.getLogger(BankService.class);

  @Value("${transactions.directory}")
  public String inputDirectory;

  private MonthStatDao monthStatDao;
  private BankTransactionDao bankTransactionDao;
  private BankTransactionUtil bankTransactionUtil;

  @Autowired
  public BankService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao) {
    this.monthStatDao = monthStatDao;
    this.bankTransactionDao = bankTransactionDao;
    bankTransactionUtil = new BankTransactionUtil();
  }

  @Override
  public List<TableObject> getTableObjects() {
    List<BankTransaction> allBankTransactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    return bankTransactionUtil.getTableObjectList(allBankTransactions);
  }

  @Override
  public void populateDatabase() {
    DirectoryReader directoryReader = new DirectoryReader();
    BankTransactionUtil bankTransactionUtil = new BankTransactionUtil();

    if(!directoryReader.isDirectoryEmpty(inputDirectory)) {
      List<BankTransaction> bankTransactionsFromDirectory = getBankTransactionsFromDirectory(bankTransactionUtil);
      for(BankTransaction bankTransaction : bankTransactionsFromDirectory) {
        bankTransactionDao.save(bankTransaction);
        setMonthStat(bankTransaction);
      }
    }
  }

  @Override
  public List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<BankTransaction>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
            (bankTransaction.getTransactiondate().getYear() == yearNumber) &&
              (bankTransaction.getCost() < 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  @Override
  public List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<BankTransaction>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
              (bankTransaction.getTransactiondate().getYear() == yearNumber) &&
              (bankTransaction.getCost() > 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  private List<BankTransaction> getBankTransactionsFromDirectory(BankTransactionUtil bankTransactionUtil) {
    List<BankTransaction> bankTransactionsFromDirectory = new ArrayList<BankTransaction>();
    try {
      bankTransactionsFromDirectory = bankTransactionUtil.getBankTransactionsFromDirectory(inputDirectory);
    } catch(ParseException e) {
      log.error("ParseException while parsing directory", e);
    } catch(IOException e) {
      log.error("IO exception while parsing directory", e);
    }
    return bankTransactionsFromDirectory;
  }

  public void setMonthStat(BankTransaction bankTransaction) {
    DecimalFormat decimalFormat = new DecimalFormat("#.00");
    double income = 0;
    double expense = 0;
    double profit;
    YearMonth yearMonth = new YearMonth(bankTransaction.getTransactiondate().getYear(),
                                         bankTransaction.getTransactiondate().getMonthOfYear());
    if(bankTransaction.getCost() > 0) {
      income = bankTransaction.getCost();
    } else {
      expense = bankTransaction.getCost();
    }
    profit = income + expense;

    if(monthStatDao.findAll().isEmpty()) {
      monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
    } else {
      MonthStat monthStatForUpdate = monthStatDao.findByYearMonth(yearMonth);
      if(monthStatForUpdate == null){
        monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
      } else {
        // TODO increase the accuracy of the below calculations, avoid double -> string -> double
        monthStatForUpdate.setIncome(Double.parseDouble(decimalFormat.format(income + monthStatForUpdate.getIncome())));
        monthStatForUpdate.setExpense(Double.parseDouble(decimalFormat.format(expense + monthStatForUpdate.getExpense())));
        monthStatForUpdate.setProfit(Double.parseDouble(decimalFormat.format(profit + monthStatForUpdate.getProfit())));
        monthStatDao.save(monthStatForUpdate);
      }
    }
  }

}
