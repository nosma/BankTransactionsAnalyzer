package com.fragmanos;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVReader;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * @author macuser on 10/9/15.
 */
public class DirectoryTests {

  private static final String INPUT_DIRECTORY = System.getProperty("user.dir") + "\\" + "src\\test\\resources\\testTransactions" + "\\";
  DirectoryReader directoryReader = new DirectoryReader();
  CSVReader csvReader = new CSVReader();

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
    List<BankTransaction> bankTransactions = getBankTransactionsFromDirectory();
    assertEquals(10,bankTransactions.size());
  }

  private List<BankTransaction> getBankTransactionsFromDirectory() throws ParseException, IOException {
    List<BankTransaction> bankTransactions = new ArrayList<BankTransaction>();
    List<String> filenameList = directoryReader.csvScanner(INPUT_DIRECTORY);
    for (String file : filenameList){
      bankTransactions.addAll(csvReader.readCSV(INPUT_DIRECTORY+file));
    }
    return bankTransactions;
  }

}
