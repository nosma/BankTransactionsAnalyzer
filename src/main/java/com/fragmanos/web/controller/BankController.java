package com.fragmanos.web.controller;

import com.fragmanos.controller.TransactionController;
import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.dao.YearStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.database.model.MonthStat;
import com.fragmanos.database.model.YearStat;
import com.fragmanos.directory.DirectoryReader;

import org.joda.time.YearMonth;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class BankController {

    @Autowired
    BankTransactionDao bankTransactionDao;

    @Autowired
    MonthStatDao monthStatDao;

    @Autowired
    YearStatDao yearStatDao;

    private static final Logger log = LoggerFactory.getLogger(BankController.class);

    @PostConstruct
    public void populateDatabase() {
        DirectoryReader directoryReader = new DirectoryReader();
        TransactionController transactionController = new TransactionController();
        String input_directory = System.getProperty("user.dir") + "\\input_files\\";

        if(!directoryReader.isDirectoryEmpty(input_directory)) {
            List<BankTransaction> bankTransactionsFromDirectory = new ArrayList<BankTransaction>();
            try {
                bankTransactionsFromDirectory = transactionController.getBankTransactionsFromDirectory(input_directory);
            } catch(ParseException e) {
                log.error("ParseException while parsing directory", e);
            } catch(IOException e) {
                log.error("IO exception while parsing directory", e);
            }
            for(BankTransaction bankTransaction : bankTransactionsFromDirectory) {
                bankTransactionDao.save(bankTransaction);
                setMonthStat(bankTransaction);
//                setYearStat(bankTransaction);
            }
        }

    }

    @RequestMapping("/api/bank/allTransactions")
    public List<TableObject> fillTable() {
        List<TableObject> dataForTable = new ArrayList<TableObject>();
        List<BankTransaction> allBankTransactions = bankTransactionDao.findAll();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        for (BankTransaction bankTransaction : allBankTransactions) {
            dataForTable.add(new TableObject(
                    dtf.print(bankTransaction.getTransactiondate()),
                    bankTransaction.getDescription(),
                    bankTransaction.getCost()
            ));
        }
        return dataForTable;
    }

    @RequestMapping("/api/statistics/monthly")
    public List<MonthStat> monthlyStatistics(){
        return monthStatDao.findAll();
    }

    @RequestMapping("/transaction")
    public List<BankTransaction> getBankTransactions() {
        return bankTransactionDao.findAll();
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

    public void setMonthStat(BankTransaction bankTransaction) {
        double income = 0;
        double expense = 0;
        double profit;
        YearMonth yearMonth = new YearMonth(bankTransaction.getTransactiondate().getYear(),
                                             bankTransaction.getTransactiondate().getMonthOfYear());
        if(bankTransaction.getCost() > 0) {
            income = bankTransaction.getCost();
        } else {
            expense = bankTransaction.getCost();
        }
        profit = income + expense;

        if(monthStatDao.findAll().isEmpty()) {
            monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
        } else {
            MonthStat monthStatForUpdate = monthStatDao.findByYearMonth(yearMonth);
            if(monthStatForUpdate == null){
                monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
            } else {
                monthStatForUpdate.setIncome(income);
                monthStatForUpdate.setExpense(expense);
                monthStatForUpdate.setProfit(profit);
                monthStatDao.save(monthStatForUpdate);
            }
        }
    }

    public void setYearStat(BankTransaction bankTransaction) {
        double income = 0;
        double expense = 0;
        double profit;

        Years year = Years.years(bankTransaction.getTransactiondate().getYear());
        if(bankTransaction.getCost() > 0) {
            income = bankTransaction.getCost();
        } else {
            expense = bankTransaction.getCost();
        }
        profit = income + expense;

        if(yearStatDao.findAll().isEmpty()){
            yearStatDao.save(new YearStat(year,income,expense,profit));
        } else {
            YearStat yearStatForUpdate = yearStatDao.findByYear(year);
            if(yearStatForUpdate == null){
                yearStatDao.save(new YearStat(year,income,expense,profit));
            } else {
                yearStatForUpdate.setIncome(income);
                yearStatForUpdate.setExpense(expense);
                yearStatForUpdate.setProfit(profit);
                yearStatDao.save(yearStatForUpdate);
            }
        }
    }

    class TableObject {
        String date;
        String description;
        Double cost;

        public TableObject(String date, String description, Double cost) {
            this.date = date;
            this.description = description;
            this.cost = cost;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getCost() {
            return cost;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }
    }

}