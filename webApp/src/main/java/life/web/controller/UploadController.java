package life.web.controller;

import life.database.model.BankTransaction;
import life.database.model.MidataTransaction;
import life.file.parser.FileParser;
import life.file.parser.MidataCsvParser;
import life.file.parser.StatementCsvParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

  private Logger log = Logger.getLogger(UploadController.class.getName());

  @Value("${transactions.directory}")
  public String inputDirectory;

  private static final String STATEMENT = "statement";
  private static final String MIDATA = "midata";
  private FileParser fileParser;
  private StorageWriter storageWriter;

  @Inject
  public UploadController(FileParser fileParser, StorageWriter storageWriter) {
    this.fileParser = fileParser;
    this.storageWriter = storageWriter;
  }

  @RequestMapping(value = "/transactions", method = RequestMethod.POST)
  @ResponseBody
  public boolean uploadTransactions(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {
    File file = getFileFromMultipart(multipartFile);
    if (fileParser.canParse(file)) {
      return fileUploadRouter(file);
    }
    throw new UnsupportedOperationException("File type not support: " + multipartFile.getOriginalFilename());
  }

  private boolean fileUploadRouter(File uploadedFile) throws IOException, ParseException {
    String fileInLowerCase = uploadedFile.getName().toLowerCase();
    boolean uploaded = false;
    if (fileInLowerCase.contains(STATEMENT)) {
      uploaded = saveStatements(uploadedFile);
    } else if (fileInLowerCase.contains(MIDATA)) {
      uploaded = saveMidata(uploadedFile);
    }
    return uploaded;
  }

  private boolean saveMidata(File file) throws IOException, ParseException {
    List<MidataTransaction> midataTransactions = new MidataCsvParser().parse(file);
    storageWriter.saveMidata(midataTransactions);
    return midataTransactions.size() > 0;
  }

  private boolean saveStatements(File file) throws IOException, ParseException {
    List<BankTransaction> bankTransactions = new StatementCsvParser().parse(file);
    storageWriter.saveTransactions(bankTransactions);
    return bankTransactions.size() > 0;
  }

  public File getFileFromMultipart(MultipartFile multipartFile) {
    String path = inputDirectory + File.separator;
    String pathToFile = path + multipartFile.getOriginalFilename();
    if (Files.notExists(Paths.get(pathToFile))) {
      createPathAndFile(path, pathToFile);
      writeFile(multipartFile, pathToFile);
    } else {
      writeFile(multipartFile, pathToFile);
    }
    return new File(pathToFile);
  }

  private void createPathAndFile(String path, String pathToFile) {
    try {
      Files.createDirectories(Paths.get(path));
      Files.createFile(Paths.get(pathToFile));
    } catch (IOException e) {
      log.error("Error while creating path and file: " + e);
    }
  }

  private void writeFile(MultipartFile multipartFile, String pathToFile) {
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(pathToFile);
      outputStream.write(multipartFile.getBytes());
      outputStream.close();
    } catch (IOException e) {
      log.error("Error while writing to file: " + e);
    }
  }

}
