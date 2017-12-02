package personal.bank.transaction.analyzer.web.controller;

import java.util.List;

public interface BankTransactionWriter<T> {
  void processTransactions(List<T> bankTransactions);

}
