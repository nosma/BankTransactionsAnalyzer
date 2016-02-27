package com.fragmanos;

import com.fragmanos.file.CSVParser;
import com.fragmanos.properties.PropertiesLoader;
import com.fragmanos.web.controller.BankInterface;
import com.fragmanos.web.controller.UploadController;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String UPLOAD_TEST_DIR_NAME = "uploadTestDir";
    private static final String STATEMENT_FILE = "statementFile.csv";
    private MockMultipartFile multipartStatementFile;
    private String statementPath;
    private File statementFile;
    private UploadController uploadController;
    private CSVParser csvParser;
    private File dir;

    @Autowired
    private BankInterface bankInterface;

    @BeforeMethod
    public void setUp() throws Exception {
        csvParser = new CSVParser();
        statementPath = UPLOAD_TEST_DIR_NAME + File.separator + STATEMENT_FILE;
        statementFile = new File(statementPath);
        dir = new File(UPLOAD_TEST_DIR_NAME);
        createStatementCsvFile();
        FileInputStream inputStatementFile = new FileInputStream(statementFile.getAbsoluteFile());
        bankInterface = mock(BankInterface.class);
        uploadController = new UploadController(new PropertiesLoader() {
            @Override
            public String getInputDirectory() {
                return UPLOAD_TEST_DIR_NAME;
            }
        });
        multipartStatementFile = new MockMultipartFile(statementFile.getName(), statementFile.getName(), "multipart/form-data", inputStatementFile);
        inputStatementFile.close();
    }

    @Test
    public void testUploadStatementFile() throws Exception {
        assertTrue(uploadController.uploadTransactions(multipartStatementFile));
    }

    @Test(dependsOnMethods = "testUploadStatementFile")
    public void testStatementFileParser() throws Exception {
        assertEquals(2, csvParser.getTransactions(System.getProperty("user.dir") + File.separator + statementPath).size());
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
