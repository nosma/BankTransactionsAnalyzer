package life.web.controller;

import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
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
    for (MidataTransaction midataTransaction : transactions) {
      bankService.setMonthStat(new BankTransaction(
          midataTransaction.getDate(),
          midataTransaction.getDescription(),
          midataTransaction.getCost()
      ));
    }
  }

}
