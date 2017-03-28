package life.web.controller;

import life.file.parser.MidataCsvParser;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

public class UploadControllerTest {

  @Inject
  private UploadController uploadController;
  private MockMultipartFile midataFile;

  @BeforeMethod
  public void setUp() throws Exception {
    uploadController = new UploadController(
        mock(MidataCsvParser.class),
        mock(BankService.class));

    String midataData = "Date,Type,Merchant/Description,Debit/Credit,Balance\n" +
        "02/08/2016,DD,AAA BBBBB,-£50.00,+£50.00\n" +
        "01/08/2016,VIS,CCCC ********** DDDDDD **********,-£10.00,+£40.00\n" +
        "31/05/2016,CR,******************,+£1000.00,+£1040.00\n" +
        "\n" +
        "Arranged overdraft limit,06/09/2016,£0.00";

    String mFile = "Midata.csv";
    midataFile = new MockMultipartFile(mFile, mFile,"text/plain", midataData.getBytes());
  }

  @Test
  public void testUploadTransactions() throws Exception {
    assertTrue(uploadController.uploadTransactions(midataFile));
  }

}