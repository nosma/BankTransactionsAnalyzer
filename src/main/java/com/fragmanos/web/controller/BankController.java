package com.fragmanos.web.controller;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.model.BankTransaction;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BankController {

    @Autowired
    BankTransactionDao bankTransactionDao;

//    @RequestMapping(value = "/transaction/{descr}" , method = RequestMethod.GET)
    @RequestMapping("/transaction")
//    public List<BankTransaction> transaction(@PathVariable String descr) {
    public List<BankTransaction> transaction() {
        return getBankTransactions();
    }

    public List<BankTransaction> getBankTransactions() {
//        BankTransaction transaction1 = new BankTransaction(new LocalDate(),"Shoes",60d);
//        BankTransaction transaction2 = new BankTransaction(new LocalDate(),"Hat",10d);
//        bankTransactionDao.saveBankTransaction(transaction1);
//        bankTransactionDao.saveBankTransaction(transaction2);

        // select all from database
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