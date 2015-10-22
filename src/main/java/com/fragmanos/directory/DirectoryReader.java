package com.fragmanos.directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author macuser on 8/22/15.
 */
public class DirectoryReader {

  public List<String> csvScanner(String directoryName) {
    List<String> csvList = new ArrayList<String>();
    File directory = new File(directoryName);
    String[] files;

    if(directory.isDirectory()) {
      files = directory.list();
      for(int i = 0; i < files.length; i++)
        if(files[i].contains(".csv"))
          csvList.add(files[i]);
    }
    return csvList;
  }

  public boolean isDirectoryEmpty(String directoryName) {
    boolean emptyDirectory = false;
    File directory = new File(directoryName);
    if(directory.list().length > 0) {
      for(String file : directory.list()) {
        if(file.contains(".csv")) emptyDirectory = false;
        else emptyDirectory = true;
      }
    }
    return emptyDirectory;
  }

}
