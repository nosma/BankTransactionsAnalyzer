package com.fragmanos;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.file.CSVParser;
import life.properties.PropertiesLoader;
import life.web.controller.BankService;
import life.web.controller.UploadController;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StatementUploadTest {

    public String USER_DIR = System.getProperty("user.dir");
    private static final String UPLOAD_TEST_DIR_NAME = "uploadTestDir";
    private static final String STATEMENT_FILE = "statementFile.csv";
    private MockMultipartFile multipartStatementFile;
    private String statementPath;
    private File statementFile;
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
        statementPath = USER_DIR + File.separator + UPLOAD_TEST_DIR_NAME + File.separator + STATEMENT_FILE;
        statementFile = new File(statementPath);
        dir = new File(UPLOAD_TEST_DIR_NAME);
        createStatementCsvFile();
        FileInputStream inputStatementFile = new FileInputStream(statementFile.getAbsoluteFile());

        uploadController = new UploadController(new PropertiesLoader() {
            @Override
            public String getInputDirectory() {
                return UPLOAD_TEST_DIR_NAME;
            }
        }, bankService);
        multipartStatementFile = new MockMultipartFile(statementFile.getName(), statementFile.getName(), "multipart/form-data", inputStatementFile);
        inputStatementFile.close();
    }

    @Test
    public void testUploadStatementFile() throws Exception {
        assertTrue(uploadController.uploadTransactions(multipartStatementFile));
    }

    @Test(dependsOnMethods = "testUploadStatementFile")
    public void testStatementFileParser() throws Exception {
        assertEquals(2, csvParser.getTransactions(statementPath).size());
    }

    private void createStatementCsvFile() throws IOException {
        if (statementFile.getParentFile().mkdir()) {
            if (statementFile.createNewFile()) {
                FileWriter writer = new FileWriter(statementFile);
                writer.write("6/30/15,NEXT RETAIL LTD STRATFORD,-30\n" +
                        "6/30/15,FORMICARY LIMITED SALARY,1462.68\n");
                writer.flush();
                writer.close();
                System.out.println("Statement file is created!");
            } else {
                System.out.println("Statement file already exists.");
            }
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (statementFile.exists() && statementFile.isFile()) {
            statementFile.delete();
        }
        dir.delete();
    }

}
