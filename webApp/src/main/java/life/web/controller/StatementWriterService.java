package life.web.controller;

import java.util.ArrayList;
import java.util.List;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementWriterService implements StatementWriter {

  private Logger log = Logger.getLogger(StatementWriterService.class.getName());

  private BankTransactionDao bankTransactionDao;
  private MonthStatService monthStatService;
  private ArrayList<BankTransaction> statementTransactions;

  @Autowired
  public StatementWriterService(BankTransactionDao bankTransactionDao, MonthStatDao monthStatDao) {
    this.bankTransactionDao = bankTransactionDao;
    monthStatService = new MonthStatService(monthStatDao, bankTransactionDao);
    statementTransactions = new ArrayList<>();
  }

  public ArrayList getStatementTransactions() {
    return statementTransactions;
  }

  @Override
  public void processTransactions(List bankTransactions) {
    saveStatementTransactions(bankTransactions);
    monthStatService.updateMonthStats();
  }

  private void saveStatementTransactions(List<BankTransaction> bankTransactions) {
    statementTransactions.addAll(bankTransactions);
    for(BankTransaction statement : bankTransactions) {
      try {
        bankTransactionDao.save(statement);
      } catch(Exception e) {
        log.error("Duplicate statement statement: " + statement.toString() + "\n" + e);
      }
    }
  }

}
