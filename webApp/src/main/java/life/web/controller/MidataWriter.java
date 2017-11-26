package life.web.controller;

import java.util.List;

import life.database.model.MidataTransaction;

public interface MidataWriter extends BankTransactionWriter<MidataTransaction> {
  void processTransactions(List<MidataTransaction> bankTransactions);
}
