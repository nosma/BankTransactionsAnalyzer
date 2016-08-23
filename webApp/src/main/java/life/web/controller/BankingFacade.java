package life.web.controller;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import life.database.dao.BankTransactionDao;
import life.database.model.BankTransaction;

@Named
class BankingFacade {

  private BankTransactionDao bankTransactionDao;
  private BankService bankService;

  @Inject
  public BankingFacade(BankTransactionDao bankTransactionDao, BankService bankService) {
    this.bankTransactionDao = bankTransactionDao;
    this.bankService = bankService;
  }

  void save(List<BankTransaction> bankTransactions) {
    bankService.saveBankTransactions(bankTransactions);
    for(BankTransaction bankTransaction : bankTransactions) {
      bankService.setMonthStat(bankTransaction);
    }
  }

}
