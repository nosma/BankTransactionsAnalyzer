package personal.bank.transaction.analyzer.web.controller;

import java.util.ArrayList;
import java.util.List;

import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.MidataTransaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidataWriterService implements MidataWriter {

  private Logger log = Logger.getLogger(MidataWriterService.class.getName());
  private MonthStatService monthStatService;
  private BankTransactionDao bankTransactionDao;
  private MidataTransactionDao midataTransactionDao;
  private ArrayList<MidataTransaction> midataTransactions;
  private ArrayList<BankTransaction> statementTransactions;

  @Autowired
  public MidataWriterService(BankTransactionDao bankTransactionDao, MidataTransactionDao midataTransactionDao, MonthStatDao monthStatDao) {
    this.bankTransactionDao = bankTransactionDao;
    this.midataTransactionDao = midataTransactionDao;
    monthStatService = new MonthStatService(monthStatDao, bankTransactionDao);
    midataTransactions = new ArrayList<>();
    statementTransactions = new ArrayList<>();
  }

  public ArrayList<MidataTransaction> getMidataTransactions() {
    return midataTransactions;
  }

  public ArrayList<BankTransaction> getStatementTransactions() {
    return statementTransactions;
  }

  @Override
  public void processTransactions(List<MidataTransaction> midataTransactions) {
    if(hasEqualSizeStatementsToMidata()) {
      saveMidataTransactions(midataTransactions);
      saveStatementTransactions(midataTransactions);
      monthStatService.updateMonthStats();
    }
  }

  private boolean hasEqualSizeStatementsToMidata() {
    return midataTransactions.size() == statementTransactions.size();
  }

  private void saveStatementTransactions(List<MidataTransaction> midataTransactions) {
    for(MidataTransaction midataTransaction : midataTransactions) {
      try {
        final BankTransaction statementTransaction = new BankTransaction(midataTransaction.getDate(), midataTransaction.getDescription(), midataTransaction.getCost());
        bankTransactionDao.save(statementTransaction);
        this.statementTransactions.add(statementTransaction);
      } catch(Exception e) {
        log.error("Duplicate Statement transaction (from midata): " + midataTransaction.toString() + "\n" + e);
      }
    }
  }

  private void saveMidataTransactions(List<MidataTransaction> midataTransactions) {
    for(MidataTransaction midataTransaction : midataTransactions) {
      try {
        midataTransactionDao.save(midataTransaction);
        this.midataTransactions.add(midataTransaction);
      } catch(Exception e) {
        log.error("Duplicate Midata transaction: " + midataTransaction.toString() + "\n" + e);
      }
    }
  }

}
