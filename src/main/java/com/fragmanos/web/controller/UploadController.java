package com.fragmanos.web.controller;

import java.io.*;

import com.fragmanos.file.CSVParser;
import com.fragmanos.properties.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload/")
public class UploadController {

  private static final Logger log = LoggerFactory.getLogger(UploadController.class);

  private static final String MIDATA_FILE_KEYWORD = "midata";
  private static final String TRANSACTION_FILE_KEYWORD = "transaction";


  private PropertiesLoader propertiesLoader;
  private CSVParser csvParser;

  @Autowired
  public UploadController(PropertiesLoader propertiesLoader) {
    this.propertiesLoader = propertiesLoader;
    csvParser = new CSVParser();
  }

  @RequestMapping(value = "transactions", method = RequestMethod.POST)
  public @ResponseBody boolean uploadTransactions(@RequestParam("file") MultipartFile file) throws IOException {
    boolean fileUploaded = false;
    if(!file.isEmpty() && file.getOriginalFilename().contains(".csv")) {
      byte[] bytes = file.getBytes();
      if(propertiesLoader.getInputDirectory() != null && file.getOriginalFilename() != null) {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(saveFileToDir(file)));
        stream.write(bytes);
        stream.close();
      }
      if(file.getOriginalFilename().contains(MIDATA_FILE_KEYWORD)) {
        file.getInputStream();
        csvParser.getMidata(propertiesLoader.getInputDirectory() + File.separator + file.getOriginalFilename());
        fileUploaded = true;
        log.info("You successfully uploaded " + file.getOriginalFilename() + "!");
      } else if(file.getOriginalFilename().contains(TRANSACTION_FILE_KEYWORD)) {
        fileUploaded = false;
        log.error("You failed to upload " + file.getOriginalFilename() + " ...");
      }
    } else {
      log.error("File for upload not in CSV format.");
    }
    return fileUploaded;
  }

  private File saveFileToDir(@RequestParam("file") MultipartFile file) {
    return new File(propertiesLoader.getInputDirectory() +File.separator+ file.getOriginalFilename());
  }

}
