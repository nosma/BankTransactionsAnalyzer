package com.fragmanos.util;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.file.CSVParser;
import com.fragmanos.web.controller.TableObject;
import org.apache.commons.collections.ListUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        for (BankTransaction bankTransaction : bankTransactionList) {
            tableObjectList.add(new TableObject(
                    dtf.print(bankTransaction.getTransactiondate()),
                    bankTransaction.getDescription(),
                    bankTransaction.getCost()
            ));
        }
        return tableObjectList;
    }

}
