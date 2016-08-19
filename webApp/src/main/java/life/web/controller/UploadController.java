package life.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import life.database.model.BankTransaction;
import life.file.parser.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload/")
public class UploadController {

  private FileParser fileParser;
  private BankingFacade bankingFacade;

  @Autowired
  public UploadController(FileParser fileParser, BankingFacade bankingFacade) {
    this.fileParser = fileParser;
    this.bankingFacade = bankingFacade;
  }

  @RequestMapping(value = "transactions", method = RequestMethod.POST)
  @ResponseBody
  public boolean uploadTransactions(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {
    if (fileParser.canParse(multipartFile)) {
      final long start = System.currentTimeMillis();
      List<BankTransaction> bankTransactions = (List<BankTransaction>) fileParser.parse(multipartFile);
      System.out.println("Parse file " + multipartFile.getOriginalFilename() + "Elapsed " + (System.currentTimeMillis() - start) + " ms");
      bankingFacade.save(bankTransactions);
      return true;
    }

    throw new UnsupportedOperationException("File type not support: " + multipartFile.getOriginalFilename());
  }

}
