package life.web.controller;

import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class BankingFacade {

  private BankService bankService;

  @Inject
  public BankingFacade(BankService bankService) {
    this.bankService = bankService;
  }

  void saveTransactions(List<BankTransaction> bankTransactions) {
    bankService.saveBankTransactions(bankTransactions);
    for (BankTransaction bankTransaction : bankTransactions) {
      bankService.setMonthStat(bankTransaction);
    }
  }

  void saveMidata(List<MidataTransaction> transactions) {
    bankService.saveMidata(transactions);
  }

}
