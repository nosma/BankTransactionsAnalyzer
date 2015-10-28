package com.fragmanos;

import java.io.IOException;
import java.text.ParseException;

import com.fragmanos.controller.TransactionController;
import com.fragmanos.directory.DirectoryReader;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * @author macuser on 10/9/15.
 */
public class DirectoryTests {

  private static final String INPUT_DIRECTORY = System.getProperty("user.dir") + "\\" + "src\\test\\resources\\testTransactions" + "\\";
  DirectoryReader directoryReader = new DirectoryReader();
  TransactionController transactionController = new TransactionController();

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
    assertEquals(20,transactionController.getBankTransactionsFromDirectory(INPUT_DIRECTORY).size());
  }

}
