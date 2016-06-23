package com.fragmanos;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.file.CSVParser;
import life.properties.PropertiesLoader;
import life.web.controller.BankService;
import life.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MidataUploadTests {

    public String USER_DIR = System.getProperty("user.dir");
    private static final String UPLOAD_TEST_DIR_NAME = "uploadTestDir";
    private static final String MIDATA_FILE = "midataFile.csv";
    private MockMultipartFile multipartMidataFile;
    private String midataPath;
    private File midataFile;
    private UploadController uploadController;
    private CSVParser csvParser;
    private File dir;
    BankService bankService;
    MonthStatDao monthStatDao;
    BankTransactionDao bankTransactionDao;

    @BeforeMethod
    public void setUp() throws Exception {
        monthStatDao = mock(MonthStatDao.class);
        bankTransactionDao = mock(BankTransactionDao.class);
        bankService = new BankService(monthStatDao,bankTransactionDao);

        csvParser = new CSVParser();
        midataPath = USER_DIR + File.separator + UPLOAD_TEST_DIR_NAME + File.separator + MIDATA_FILE;
        midataFile = new File(midataPath);
        dir = new File(UPLOAD_TEST_DIR_NAME);
        createMidataCsvFile();

        FileInputStream inputMidataFile = new FileInputStream(midataFile.getAbsoluteFile());

        uploadController = new UploadController(new PropertiesLoader() {
            @Override
            public String getInputDirectory() {
                return UPLOAD_TEST_DIR_NAME;
            }
        }, bankService);

        multipartMidataFile = new MockMultipartFile(midataFile.getName(), midataFile.getName(), "multipart/form-data", inputMidataFile);
        inputMidataFile.close();
    }

    @Test
    public void testUploadMidataFile() throws Exception {
        assertTrue(uploadController.uploadTransactions(multipartMidataFile));
    }

    @Test(dependsOnMethods = "testUploadMidataFile")
    public void testMidataFileParser() throws Exception {
        assertEquals(2, csvParser.getMidata(midataPath).size());
    }

    private void createMidataCsvFile() throws IOException {
        if (midataFile.getParentFile().mkdir()) {
            if (midataFile.createNewFile()) {
                FileWriter writer = new FileWriter(midataFile);
                writer.write("Date,Type,Merchant/Description,Debit/Credit,Balance\n" +
                        "15/01/2016,VIS,INT'L **********  Amazon UK Retail  AMAZON.CO.UK,-£84.35,+£1005.26\n" +
                        "15/01/2016,VIS,INT'L **********  Amazon UK Marketpl***-***-****,-£31.12,+£1089.61\n");
                writer.flush();
                writer.close();
                System.out.println("Midata file is created!");
            } else {
                System.out.println("Midata file  already exists.");
            }
        }

    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (midataFile.exists() && midataFile.isFile()) {
            midataFile.delete();
        }
        dir.delete();
    }
}
