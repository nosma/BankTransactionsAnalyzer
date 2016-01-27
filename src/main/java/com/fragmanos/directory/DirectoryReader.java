package com.fragmanos.directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryReader {

  public List<String> csvScanner(String directoryName) {
    List<String> csvList = new ArrayList<String>();
    File directory = new File(directoryName);
    String[] files;

    if(directory.isDirectory()) {
      files = directory.list();
      for(String file : files)
        if(file.contains(".csv"))
          csvList.add(file);
    }
    return csvList;
  }

  public boolean isDirectoryEmpty(String directoryName) {
    boolean emptyDirectory = false;
    File directory = new File(directoryName);
    if(directory.list().length > 0) {
      for(String file : directory.list()) {
        emptyDirectory = !file.contains(".csv");
      }
    }
    return emptyDirectory;
  }

}
