package personal.bank.transaction.analyzer.web.controller;

import java.util.List;

import personal.bank.transaction.analyzer.database.model.BankTransaction;

public interface StatementWriter extends BankTransactionWriter<BankTransaction> {
  void processTransactions(List<BankTransaction> bankTransactions);
}
