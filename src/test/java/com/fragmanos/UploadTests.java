package com.fragmanos;

import java.io.*;

import com.fragmanos.file.CSVParser;
import com.fragmanos.properties.PropertiesLoader;
import com.fragmanos.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UploadTests {

  private static final String MIDATA_FILE = "testmidataFile.csv";
  private static final String UPLOAD_TEST_DIR_NAME = "uploadTestDir";
  private CSVParser csvParser;
  private UploadController uploadController;
  private File midataFile;
  private File dir;
  private MockMultipartFile multipartFile;
  private String midataPath;
  @BeforeMethod
  public void setUp() throws Exception {
    csvParser = new CSVParser();
    midataPath = UPLOAD_TEST_DIR_NAME + File.separator + MIDATA_FILE;
    dir = new File(UPLOAD_TEST_DIR_NAME);
    midataFile = new File(midataPath);
    createMidataCsvFile();
    FileInputStream inputFile = new FileInputStream(midataFile.getAbsoluteFile());

    uploadController = new UploadController(new PropertiesLoader() {
      @Override
      public String getInputDirectory() { return UPLOAD_TEST_DIR_NAME; }
    });
    multipartFile = new MockMultipartFile(midataFile.getName(), midataFile.getName(), "multipart/form-data", inputFile);
  }

  @Test
  public void testUploadFile() throws Exception {
    assertTrue(uploadController.uploadTransactions(multipartFile));
  }

  @Test(dependsOnMethods = "testUploadFile")
  public void testMidataFileParser() throws Exception {
    assertEquals(2, csvParser.getMidata(System.getProperty("user.dir") + File.separator + midataPath).size());
  }

  private void createMidataCsvFile() throws IOException {
    if(midataFile.getParentFile().mkdir()){
      if(midataFile.createNewFile()) {
        FileWriter writer = new FileWriter(midataFile);
        writer.write("Date,Type,Merchant/Description,Debit/Credit,Balance\n" +
                       "11/01/2016,VIS,INT'L **********  Amazon UK Retail  AMAZON.CO.UK,-£84.35,+£1005.26\n" +
                       "11/01/2016,VIS,INT'L **********  Amazon UK Marketpl***-***-****,-£31.12,+£1089.61\n");
        writer.flush();
        writer.close();
        System.out.println("File is created!");
      } else {
        System.out.println("File already exists.");
      }
    }

  }

  @AfterMethod
  public void tearDown() throws Exception {
    if(midataFile.exists() && midataFile.isFile()) {
      midataFile.delete();
    }
    dir.delete();
  }
}
