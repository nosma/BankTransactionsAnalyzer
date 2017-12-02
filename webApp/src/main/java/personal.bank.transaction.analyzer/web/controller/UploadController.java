package personal.bank.transaction.analyzer.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;

import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.MidataTransaction;
import personal.bank.transaction.analyzer.file.parser.FileParser;
import personal.bank.transaction.analyzer.file.parser.MidataCsvParser;
import personal.bank.transaction.analyzer.file.parser.StatementCsvParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

  private static final String STATEMENT = "statement";
  private static final String MIDATA = "midata";
  @Value("${transactions.directory}")
  public String inputDirectory;
  private Logger log = Logger.getLogger(UploadController.class.getName());
  private FileParser fileParser;
  private StatementWriter statementWriter;
  private MidataWriter midataWriter;

  @Inject
  public UploadController(FileParser fileParser, StatementWriter statementWriter, MidataWriter midataWriter) {
    this.fileParser = fileParser;
    this.statementWriter = statementWriter;
    this.midataWriter = midataWriter;
  }

  @RequestMapping(value = "/transactions", method = RequestMethod.POST)
  @ResponseBody
  public void uploadTransactions(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {
    File file = getFileFromMultipart(multipartFile);
    if(fileParser.canParse(file)) {
      fileUploadRouter(file);
    } else {
      throw new UnsupportedOperationException("File type not supported: " + multipartFile.getOriginalFilename());
    }
  }

  private boolean fileUploadRouter(File uploadedFile) throws IOException, ParseException {
    String fileInLowerCase = uploadedFile.getName().toLowerCase();
    boolean uploaded = false;
    if(fileInLowerCase.contains(STATEMENT)) {
      uploaded = saveStatements(uploadedFile);
    } else if(fileInLowerCase.contains(MIDATA)) {
      uploaded = saveMidata(uploadedFile);
    }
    return uploaded;
  }

  private boolean saveMidata(File file) throws IOException, ParseException {
    List<MidataTransaction> midataTransactions = new MidataCsvParser().parse(file);
    midataWriter.processTransactions(midataTransactions);
    return midataTransactions.size() > 0;
  }

  private boolean saveStatements(File file) throws IOException, ParseException {
    List<BankTransaction> statements = new StatementCsvParser().parse(file);
    statementWriter.processTransactions(statements);
    return statements.size() > 0;
  }

  public File getFileFromMultipart(MultipartFile multipartFile) {
    String path = inputDirectory + File.separator;
    String pathToFile = path + multipartFile.getOriginalFilename();
    if(Files.notExists(Paths.get(pathToFile))) {
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
    } catch(IOException e) {
      log.error("Error while creating path and file: " + e);
    }
  }

  private void writeFile(MultipartFile multipartFile, String pathToFile) {
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(pathToFile);
      outputStream.write(multipartFile.getBytes());
      outputStream.close();
    } catch(IOException e) {
      log.error("Error while writing to file: " + e);
    }
  }

}
