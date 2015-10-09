package com.fragmanos;

import com.fragmanos.directory.DirectoryReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author macuser on 10/9/15.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AppConfig.class}):
public class DirectoryTests {

    private static final String INPUT_DIRECTORY = "input_files";

    DirectoryReader directoryReader;

    @Test
    public void readCSVsFromDirectory(){
        String csvFilesDirectory = System.getProperty("user.dir") + "\\" + INPUT_DIRECTORY + "\\";
        assertEquals(6,  directoryReader.csvScanner(csvFilesDirectory));
    }



}
