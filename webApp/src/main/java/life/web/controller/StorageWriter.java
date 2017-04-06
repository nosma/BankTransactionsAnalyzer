package life.web.controller;

import java.util.List;

import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;

public interface StorageWriter {

  void saveTransactions(List<BankTransaction> bankTransactions);

  void saveMidata(List<MidataTransaction> bankTransactions);

}
