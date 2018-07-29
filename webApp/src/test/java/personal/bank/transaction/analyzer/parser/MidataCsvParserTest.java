package personal.bank.transaction.analyzer.parser;

import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.web.controller.UploadController;
import personal.bank.transaction.analyzer.web.service.MidataWriterService;
import personal.bank.transaction.analyzer.web.service.StatementWriterService;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class MidataCsvParserTest {

  private MidataCsvParser midataCsvParser;
  private File mFile;
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
    midataCsvParser = new MidataCsvParser();
    String fileName = "Midata.csv";
    String midataData = "Date,Type,Merchant/Description,Debit/Credit,Balance\r" +
                        "02/08/2016,DD,AAA BBBBB,-£50.00,+£50.00\r" +
                        "01/08/2016,VIS,CCCC ********** DDDDDD **********,-£10.00,+£40.00\r" +
                        "31/05/2016,CR,******************,+£1000.00,+£1040.00\r" +
                        "\r" +
                        "Arranged overdraft limit,06/09/2016,£0.00";

    MockMultipartFile mockMidataFile = new MockMultipartFile(fileName, fileName, "text/plain", midataData.getBytes());
    uploadController.inputDirectory = "input_files";
    mFile = uploadController.getFileFromMultipart(mockMidataFile);
  }

  @Test
  public void testCanParseMidata() throws Exception {
    assertEquals(true, midataCsvParser.canParse(mFile));
  }

  @Test
  public void testParseMidata() throws Exception {
    assertEquals(3, midataCsvParser.parse(mFile).size());
  }

  @AfterMethod
  public void tearDown() throws Exception {
    mFile.delete();
  }
}