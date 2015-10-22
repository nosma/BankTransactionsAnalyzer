package com.fragmanos;

import com.fragmanos.directory.DirectoryReader;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * @author macuser on 10/9/15.
 */
public class DirectoryTests {

  private static final String INPUT_DIRECTORY = System.getProperty("user.dir") + "\\" + "input_files" + "\\";

  DirectoryReader directoryReader = new DirectoryReader();

  @Test
  public void checkIfCSVDirectoryIsEmpty() {
    assertFalse(directoryReader.isDirectoryEmpty(INPUT_DIRECTORY));
  }

  @Test(dependsOnMethods = "checkIfCSVDirectoryIsEmpty")
  public void readCSVsFromDirectory() {
    assertEquals(1, directoryReader.csvScanner(INPUT_DIRECTORY).size());
  }

}
