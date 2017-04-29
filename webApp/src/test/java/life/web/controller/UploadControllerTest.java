package life.web.controller;

import javax.inject.Inject;
import life.file.parser.MidataCsvParser;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

public class UploadControllerTest {

  @Inject
  private UploadController uploadController;
  private MockMultipartFile midataFile;
  private MockMultipartFile statementsFile;

  @BeforeClass
  public void setUp() throws Exception {
    uploadController = new UploadController(
        mock(MidataCsvParser.class),
        mock(StorageWriterService.class));

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
    midataFile = new MockMultipartFile(mFile, mFile,"text/plain", midataData.getBytes());
    statementsFile = new MockMultipartFile(bFile, bFile,"text/plain", statementsData.getBytes());
  }

  @Test
  public void testUploadTransactions() throws Exception {
    assertTrue(uploadController.uploadTransactions(midataFile));
  }

  @Test
  public void testUploadMidata() throws Exception {
    assertTrue(uploadController.uploadTransactions(statementsFile));
  }
}