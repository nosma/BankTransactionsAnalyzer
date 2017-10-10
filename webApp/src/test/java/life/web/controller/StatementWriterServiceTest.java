package life.web.controller;

import life.database.dao.BankTransactionDao;
import life.database.dao.MidataTransactionDao;
import life.database.dao.MonthStatDao;
import life.file.parser.FileParser;
import life.file.parser.StatementCsvParser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class StatementWriterServiceTest {

  private StatementWriterService service;
  private UploadController uploadController;
  private StatementWriter statementWriter;
  private MidataWriter midataWriter;

  @BeforeMethod
  public void setUp() throws Exception {
    BankTransactionDao bankTransactionDao = mock(BankTransactionDao.class);
    MidataTransactionDao midataTransactionDao = mock(MidataTransactionDao.class);
    MonthStatDao monthStatDao = mock(MonthStatDao.class);
    service = new StatementWriterService(bankTransactionDao, monthStatDao);

    FileParser fileParser = new StatementCsvParser();

    statementWriter = new StatementWriterService(bankTransactionDao,monthStatDao);
    midataWriter = new MidataWriterService(bankTransactionDao,midataTransactionDao,monthStatDao);
    uploadController = new UploadController(fileParser, statementWriter, midataWriter);
//    uploadController.getFileFromMultipart()
  }

  @Test
  public void testProcessStatements() throws Exception {
//    service.getStatementTransactions()
  }

}