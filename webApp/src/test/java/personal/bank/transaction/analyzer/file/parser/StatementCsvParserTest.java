package personal.bank.transaction.analyzer.file.parser;

import java.io.File;

import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.web.service.MidataWriterService;
import personal.bank.transaction.analyzer.web.service.StatementWriterService;
import personal.bank.transaction.analyzer.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;


public class StatementCsvParserTest {

  private StatementCsvParser csvParser;
  private File file;
  private StatementWriterService statementWriter;
  private MidataWriterService midataWriter;
  private BankTransactionDao bankTransactionDao;
  private MidataTransactionDao midataTransactionDao;
  private MonthStatDao monthStatDao;

  @BeforeMethod
  public void setUp() throws Exception {
    bankTransactionDao = mock(BankTransactionDao.class);
    midataTransactionDao = mock(MidataTransactionDao.class);
    monthStatDao = mock(MonthStatDao.class);
    statementWriter = new StatementWriterService(bankTransactionDao, monthStatDao);
    midataWriter = new MidataWriterService(bankTransactionDao, midataTransactionDao, monthStatDao);
    UploadController uploadController = new UploadController(mock(FileParser.class), statementWriter, midataWriter);
    csvParser = new StatementCsvParser();
    String sFile = "Statement.csv";
    String statementData = "2015-12-03,TFL.GOV.UK/CP TFL TRAVEL CH,-4.40\n" +
        "2015-12-03,POD LONDON EC1Y,-5.29\n" +
        "2015-12-03,MAPLIN TOTTENMHALE LONDON,-59.99\n";

    MockMultipartFile statementFile = new MockMultipartFile(sFile, sFile, "text/plain", statementData.getBytes());
    file = uploadController.getFileFromMultipart(statementFile);
  }

  @Test
  public void testCanParseStatement() throws Exception {
    assertEquals(true, csvParser.canParse(file));
  }

  @Test
  public void testParseStatement() throws Exception {
    assertEquals(3, csvParser.parse(file).size());
  }

  @AfterMethod
  public void tearDown() throws Exception {
    file.delete();
  }

}