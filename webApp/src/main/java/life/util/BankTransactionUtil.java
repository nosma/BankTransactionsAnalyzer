package life.util;

import life.database.model.BankTransaction;
import life.directory.DirectoryReader;
import life.file.CSVParser;
import life.web.controller.TableObject;
import org.apache.commons.collections.ListUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class BankTransactionUtil {

  List<BankTransaction> totalBankTransactions = new ArrayList<>();
  CSVParser csvParser = new CSVParser();
  DirectoryReader directoryReader = new DirectoryReader();

  public List<BankTransaction> getBankTransactionsFromDirectory(String inputDirectory) throws ParseException, IOException {
    for (String file : directoryReader.csvScanner(inputDirectory)) {
      saveStatementToDB(inputDirectory, file);
    }
    return totalBankTransactions;
  }

  public void saveStatementToDB(String inputDirectory, String file) throws ParseException, IOException {
    List<BankTransaction> fileBankTransactionList = csvParser.getTransactions(inputDirectory + File.separator+ file);
    totalBankTransactions = unionOfBankTransactions(totalBankTransactions, fileBankTransactionList);
  }

  public List<BankTransaction> unionOfBankTransactions(List<BankTransaction> totalBankTransactionList, List<BankTransaction> fileBankTransactionList) {
    Set<BankTransaction> set = new HashSet<>();
    set.addAll(totalBankTransactionList);
    set.addAll(fileBankTransactionList);
    return new ArrayList<>(set);
  }

  public List<BankTransaction> differenceOfBankTransactions(List<BankTransaction> listA, List<BankTransaction> listB){
    return ListUtils.removeAll(listA, listB);
  }

   public List<TableObject> getTableObjectList(List<BankTransaction> bankTransactionList) {
        List<TableObject> tableObjectList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (BankTransaction bankTransaction : bankTransactionList) {
            tableObjectList.add(new TableObject(
                    bankTransaction.getTransactiondate().toString(),
                    bankTransaction.getDescription(),
                    bankTransaction.getCost()
            ));
        }
        return tableObjectList;
    }


}
