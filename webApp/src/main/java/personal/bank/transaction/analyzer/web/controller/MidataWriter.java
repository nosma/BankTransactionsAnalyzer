package personal.bank.transaction.analyzer.web.controller;

import java.util.List;

import personal.bank.transaction.analyzer.database.model.MidataTransaction;

public interface MidataWriter extends BankTransactionWriter<MidataTransaction> {
  void processTransactions(List<MidataTransaction> bankTransactions);
}
