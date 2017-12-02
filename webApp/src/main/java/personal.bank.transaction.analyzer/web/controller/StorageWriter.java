package personal.bank.transaction.analyzer.web.controller;

import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.MidataTransaction;

import java.util.List;

public interface StorageWriter {

  void saveTransactions(List<BankTransaction> bankTransactions);

  void saveMidata(List<MidataTransaction> bankTransactions);

}
