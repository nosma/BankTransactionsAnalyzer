package com.fragmanos;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import com.fragmanos.controller.BankTransactionUtil;
import com.fragmanos.directory.DirectoryReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class DirectoryTests {

  private final String INPUT_DIRECTORY = System.getProperty("user.dir") + getTestPath();
  private DirectoryReader directoryReader;
  private BankTransactionUtil bankTransactionUtil;

  @BeforeMethod
  public void setUp() throws Exception {
    directoryReader = new DirectoryReader();
    bankTransactionUtil = new BankTransactionUtil();
  }

  @Test
  public void checkIfCSVDirectoryIsEmpty() {
    assertFalse(directoryReader.isDirectoryEmpty(INPUT_DIRECTORY));
  }

  @Test(dependsOnMethods = "checkIfCSVDirectoryIsEmpty")
  public void readCSVsFromDirectory() {
    assertEquals(2, directoryReader.csvScanner(INPUT_DIRECTORY).size());
  }

  @Test(dependsOnMethods = "readCSVsFromDirectory")
  public void readCSVFile() throws IOException, ParseException {
    assertEquals(36, bankTransactionUtil.getBankTransactionsFromDirectory(INPUT_DIRECTORY).size());
  }

  public String getTestPath() {
    String fs = File.separator;
    return fs+"src"+fs+"test"+fs+"resources"+fs+"testTransactions"+fs;
  }
}
