package com.fragmanos.web.controller;

import com.fragmanos.controller.TransactionController;
import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BankController {

    @Autowired
    BankTransactionDao bankTransactionDao;

    @RequestMapping("/api/bank/allTransactions")
    public List<BankTransaction> fillTable() throws IOException, ParseException {
        DirectoryReader directoryReader = new DirectoryReader();
        TransactionController transactionController = new TransactionController();
        String input_directory = System.getProperty("user.dir") + "/input_files/";

        if(!directoryReader.isDirectoryEmpty(input_directory)){
            List<BankTransaction> bankTransactionsFromDirectory = transactionController.getBankTransactionsFromDirectory(input_directory);
            for (BankTransaction bt : bankTransactionsFromDirectory){
               bankTransactionDao.saveBankTransaction(bt);
           }
        }

    return bankTransactionDao.findAllBankTransactions();
    }

//    @RequestMapping(value = "/transaction/{descr}" , method = RequestMethod.GET)
    @RequestMapping("/transaction")
//    public List<BankTransaction> transaction(@PathVariable String descr) {
    public List<BankTransaction> transaction() {
        return getBankTransactions();
    }

    public List<BankTransaction> getBankTransactions() {
        List<BankTransaction> transactionList = new ArrayList<BankTransaction>();
        List<BankTransaction> allBankTransactions = bankTransactionDao.findAllBankTransactions();
        for(BankTransaction myTransaction : allBankTransactions){
            printTransactionStatement(myTransaction);
            transactionList.add(myTransaction);
        }
        return transactionList;
    }

    private static void printTransactionStatement(BankTransaction myTransaction) {
        StringBuilder sb = new StringBuilder("");
        String line = "\n===========================";
        sb.append("\nTransaction")
          .append(line)
          .append("\nDate: ").append(myTransaction.getTransactiondate())
          .append("\nDescription: ").append(myTransaction.getDescription())
          .append("\nCost: ").append(myTransaction.getCost())
          .append(line);
        System.out.println(sb);
    }
}