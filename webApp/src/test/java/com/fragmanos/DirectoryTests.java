package com.fragmanos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import life.directory.DirectoryReader;
import life.util.BankTransactionUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class DirectoryTests {

  private final String INPUT_DIRECTORY = System.getProperty("user.dir") + getTestPath();
  private final String FILENAME = "BankTransactions.csv";
  private DirectoryReader directoryReader;
  private BankTransactionUtil bankTransactionUtil;
  private File transactionsFile;

  @BeforeClass
  public void setUp() throws Exception {
    directoryReader = new DirectoryReader();
    bankTransactionUtil = new BankTransactionUtil();
    transactionsFile = new File(Paths.get(INPUT_DIRECTORY+File.separator+FILENAME).toString());
    createTestDir();
  }

  @AfterClass
  public void tearDown() throws Exception {
    //TODO Delete the directory also, not only the file ...
    if (transactionsFile.exists())
      System.out.println((transactionsFile.delete()) ? "File Deleted." : "File NOT Deleted.");
  }

  private void createTestDir() {
    if(!transactionsFile.getParentFile().mkdir()) {
      try {
        if(transactionsFile.createNewFile()){
          fillFile(transactionsFile);
        }
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void fillFile(File transactionsFile) {
    try {
      if (transactionsFile.exists()) {
        FileWriter writer = new FileWriter(transactionsFile);
        writer.write("6/30/15,NEXT RETAIL LTD STRATFORD,-30\n" +
                       "6/30/15,FORMICARY LIMITED SALARY,1462.68\n");
        writer.flush();
        writer.close();
        System.out.println("Transactions file created!");
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void checkIfCSVDirectoryIsEmpty() {
    assertFalse(directoryReader.isDirectoryEmpty(INPUT_DIRECTORY));
  }

  @Test(dependsOnMethods = "checkIfCSVDirectoryIsEmpty")
  public void readCSVsFromDirectory() {
    assertEquals(1, directoryReader.csvScanner(INPUT_DIRECTORY).size());
  }

  @Test(dependsOnMethods = "readCSVsFromDirectory")
  public void readCSVFile() throws IOException, ParseException {
    assertEquals(2, bankTransactionUtil.getBankTransactionsFromDirectory(INPUT_DIRECTORY).size());
  }

  private String getTestPath() {
    return Paths.get(File.separator+"testDirectory").toString();
  }
}
