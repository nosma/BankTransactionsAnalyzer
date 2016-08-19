package life.file.parser;

import java.util.List;

import life.database.model.BankTransaction;
import org.springframework.web.multipart.MultipartFile;

public interface TransactionFileParser extends FileParser<MultipartFile, List<BankTransaction>> {
}
