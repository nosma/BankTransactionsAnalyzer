package com.fragmanos;

import com.fragmanos.controller.BankTransactionUtil;
import com.fragmanos.directory.DirectoryReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DirectoryTests {
  //  Read directory from properties file is not suggested for tests
  private static final String INPUT_DIRECTORY = System.getProperty("user.dir") + "/src/test/resources/testTransactions/";

  DirectoryReader directoryReader = new DirectoryReader();
  BankTransactionUtil bankTransactionUtil = new BankTransactionUtil();

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

}
