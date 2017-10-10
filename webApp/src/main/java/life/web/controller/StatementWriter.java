package life.web.controller;

import java.util.List;

import life.database.model.BankTransaction;

public interface StatementWriter extends BankTransactionWriter<BankTransaction> {
  void processTransactions(List<BankTransaction> bankTransactions);
}
