package life.web.controller;

import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;

import java.util.List;

public interface StorageWriter {

  void saveTransactions(List<BankTransaction> bankTransactions);

  void saveMidata(List<MidataTransaction> bankTransactions);

}
