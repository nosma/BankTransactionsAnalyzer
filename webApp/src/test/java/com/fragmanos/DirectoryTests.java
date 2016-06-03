package com.fragmanos;

import life.directory.DirectoryReader;
import life.util.BankTransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DirectoryTests {

  private static final Logger log = LoggerFactory.getLogger(DirectoryTests.class);

  private final String TEST_FOLDER = "testFolder";
  private final String TEST_DIRECTORY = System.getProperty("user.dir") + getTestFolderDir();
  private final String FILENAME = "BankTransactions.csv";
  private DirectoryReader directoryReader;
  private BankTransactionUtil bankTransactionUtil;
  private File transactionsFile;

  @BeforeClass
  public void setUp() throws Exception {
    directoryReader = new DirectoryReader();
    bankTransactionUtil = new BankTransactionUtil();
    transactionsFile = new File(Paths.get(TEST_DIRECTORY +File.separator+FILENAME).toString());
    createTestDir();
    writeToFile(transactionsFile);
  }

  @AfterClass
  public void tearDown() throws Exception {
    if (transactionsFile.exists())
      log.debug((transactionsFile.delete()) ? "File Deleted." : "File NOT Deleted.");
    log.debug((Files.deleteIfExists(Paths.get(TEST_DIRECTORY))) ? "Dir Deleted." : "Dir NOT Deleted.");
  }

  @Test
  public void checkIfCSVDirectoryIsEmpty() {
    assertFalse(directoryReader.isDirectoryEmpty(TEST_DIRECTORY));
  }

  @Test(dependsOnMethods = "checkIfCSVDirectoryIsEmpty")
  public void readCSVsFromDirectory() {
    assertEquals(1, directoryReader.csvScanner(TEST_DIRECTORY).size());
  }

  @Test(dependsOnMethods = "readCSVsFromDirectory")
  public void readCSVFile() throws IOException, ParseException {
    assertEquals(2, bankTransactionUtil.getBankTransactionsFromDirectory(TEST_DIRECTORY).size());
  }

  private String getTestFolderDir() {
    return Paths.get(File.separator+ TEST_FOLDER).toString();
  }

  private void createTestDir() {
    try {
      Files.createDirectory(Paths.get(TEST_DIRECTORY));
    } catch (IOException e) {
      log.error("Error while creating test directory\n" + e);
    }
  }

  private void writeToFile(File transactionsFile) {
    try {
      if (!transactionsFile.exists()) {
        if(transactionsFile.createNewFile()){
          FileWriter writer = new FileWriter(transactionsFile);
          writer.write("6/30/15,NEXT RETAIL LTD STRATFORD,-30\n" +
                         "6/30/15,FORMICARY LIMITED SALARY,1462.68\n");
          writer.flush();
          writer.close();
          System.out.println("Transactions file created!");
        } else {
          log.error("File " + transactionsFile.getName() + " did not created.");
        }
      } else { log.warn("File " + transactionsFile.getName() + " already exists."); }
    } catch(IOException e) {
      log.error("Error during file creation "+e);
    }
  }

}
