package personal.bank.transaction.analyzer.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.util.BankTransactionUtil;
import org.springframework.stereotype.Component;

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



}


