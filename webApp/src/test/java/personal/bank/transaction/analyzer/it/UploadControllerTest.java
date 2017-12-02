package personal.bank.transaction.analyzer.it;

import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.file.parser.MidataCsvParser;
import personal.bank.transaction.analyzer.web.controller.MidataWriterService;
import personal.bank.transaction.analyzer.web.controller.StatementWriterService;
import personal.bank.transaction.analyzer.web.controller.UploadController;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class UploadControllerTest {

  @Inject
  private UploadController uploadController;
  private MockMultipartFile midataFile;
  private MockMultipartFile statementsFile;

  private StatementWriterService statementWriter;
  private MidataWriterService midataWriter;

  private BankTransactionDao bankTransactionDao;
  private MidataTransactionDao midataTransactionDao;
  private MonthStatDao monthStatDao;

  @BeforeClass
  public void setUp() throws Exception {

    String midataData = "Date,Type,Merchant/Description,Debit/Credit,Balance\n" +
                            "02/08/2016,DD,AAA BBBBB,-£50.00,+£50.00\n" +
                            "01/08/2016,VIS,CCCC ********** DDDDDD **********,-£10.00,+£40.00\n" +
                            "31/05/2016,CR,******************,+£1000.00,+£1040.00\n" +
                            "\n" +
                            "Arranged overdraft limit,06/09/2016,£0.00";

    String statementsData = "03/01/2016,VIRGIN ACTIVE,54.98\n" +
                                "03/01/2016,L.B. HACKNEY CTAX,90.00\n" +
                                "03/01/2016,CASH HSBC01,200.00";

    String mFile = "Midata.csv";
    String bFile = "Statement.csv";
    midataFile = new MockMultipartFile(mFile, mFile, "text/plain", midataData.getBytes());
    statementsFile = new MockMultipartFile(bFile, bFile, "text/plain", statementsData.getBytes());

    bankTransactionDao = mock(BankTransactionDao.class);
    midataTransactionDao = mock(MidataTransactionDao.class);
    monthStatDao = mock(MonthStatDao.class);
    statementWriter = new StatementWriterService(bankTransactionDao, monthStatDao);
    midataWriter = new MidataWriterService(bankTransactionDao, midataTransactionDao, monthStatDao);
    uploadController = new UploadController(mock(MidataCsvParser.class), statementWriter, midataWriter);
    mockMidataDaoRecords();
  }

  @Test
  public void testUploadTransactions() throws Exception {
    uploadController.uploadTransactions(statementsFile);
    assertEquals(3, statementWriter.getStatementTransactions().size());
  }

  @Test
  public void testUploadMidata() throws Exception {
    uploadController.uploadTransactions(midataFile);
    assertEquals(3, midataWriter.getMidataTransactions().size());
    assertEquals(3, midataWriter.getStatementTransactions().size());
  }

  private void mockMidataDaoRecords() {
    List<BankTransaction> bankTransactions = new ArrayList<>();
    bankTransactions.add(new BankTransaction(LocalDate.of(2016, 1, 3),"VIRGIN ACTIVE",59.84));
    bankTransactions.add(new BankTransaction(LocalDate.of(2016, 1, 3),"L.B. HACKNEY CTAX",90.0));
    bankTransactions.add(new BankTransaction(LocalDate.of(2016, 1, 3),"CASH HSBC01",200.00));
    when(bankTransactionDao.findAll()).thenReturn(bankTransactions);
  }
}