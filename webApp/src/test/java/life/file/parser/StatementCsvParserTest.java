package life.file.parser;

import life.web.controller.BankService;
import life.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;


public class StatementCsvParserTest {

  private StatementCsvParser csvParser;
  private File file;

  @BeforeMethod
  public void setUp() throws Exception {
    UploadController uploadController = new UploadController(
        mock(FileParser.class),
        mock(BankService.class));
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