package life.file.parser;

import life.web.controller.BankingFacade;
import life.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class MidataCsvParserTest {

  private MidataCsvParser midataCsvParser;
  private File mFile;

  @BeforeMethod
  public void setUp() throws Exception {
    UploadController uploadController = new UploadController(
        mock(FileParser.class),
        mock(BankingFacade.class));
    midataCsvParser = new MidataCsvParser();
    String fileName = "Midata.csv";
    String midataData = "Date,Type,Merchant/Description,Debit/Credit,Balance\r" +
                        "02/08/2016,DD,AAA BBBBB,-£50.00,+£50.00\r" +
                        "01/08/2016,VIS,CCCC ********** DDDDDD **********,-£10.00,+£40.00\r" +
                        "31/05/2016,CR,******************,+£1000.00,+£1040.00\r" +
                        "\r" +
                        "Arranged overdraft limit,06/09/2016,£0.00";

    MockMultipartFile mockMidataFile = new MockMultipartFile(fileName, fileName, "text/plain", midataData.getBytes());
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