package com.fragmanos.web.controller;

import java.io.*;
import java.text.ParseException;
import java.util.List;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.file.CSVParser;
import com.fragmanos.properties.PropertiesLoader;
import com.fragmanos.util.BankTransactionUtil;
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
  private static final String STATEMENT_FILE_KEYWORD = "statement";

  private PropertiesLoader propertiesLoader;
  private CSVParser csvParser;
  private BankTransactionUtil btutil;

  private BankService bankService;

  @Autowired
  public UploadController(PropertiesLoader propertiesLoader, BankService bankService) {
    this.propertiesLoader = propertiesLoader;
    this.bankService = bankService;
    csvParser = new CSVParser();
    btutil = new BankTransactionUtil();
  }

  @RequestMapping(value = "transactions", method = RequestMethod.POST)
  public
  @ResponseBody
  boolean uploadTransactions(@RequestParam("file") MultipartFile file) throws IOException, ParseException {

    boolean fileUploaded = false;
    if (!file.isEmpty() && file.getOriginalFilename().contains(".csv")) {
      byte[] bytes = file.getBytes();
      if (propertiesLoader.getInputDirectory() != null && file.getOriginalFilename() != null) {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(saveFileToDir(file)));
        stream.write(bytes);
        stream.close();
      }
      file.getInputStream();
      saveFileDataToDatabase(file);
      fileUploaded = true;
      log.info("You successfully uploaded " + file.getOriginalFilename() + "!");
    } else {
      log.error("File for upload not in CSV format.");
    }
    return fileUploaded;
  }

  private void saveFileDataToDatabase(@RequestParam("file") MultipartFile file) throws ParseException, IOException {
    List<BankTransaction> databaseTransactions = bankService.getDbBankTransactions();
    List<BankTransaction> bankTransactionDifferenceList;

    if (file.getOriginalFilename().contains(MIDATA_FILE_KEYWORD)) {
      bankTransactionDifferenceList = btutil.differenceOfBankTransactions(databaseTransactions, csvParser.getMidata(getFilePath(file)));
      bankService.populateDatabase(bankTransactionDifferenceList);
    } else if (file.getOriginalFilename().contains(STATEMENT_FILE_KEYWORD)) {
      bankTransactionDifferenceList = btutil.differenceOfBankTransactions(databaseTransactions,csvParser.getTransactions(getFilePath(file)));
      bankService.populateDatabase(bankTransactionDifferenceList);
    } else {
      log.error("Specified file type has not saved " + file.getOriginalFilename() + " ...");
    }
  }

  private String getFilePath(@RequestParam("file") MultipartFile file) {
    return propertiesLoader.getInputDirectory() + File.separator + file.getOriginalFilename();
  }

  private File saveFileToDir(@RequestParam("file") MultipartFile file) {
    return new File(getFilePath(file));
  }

}
