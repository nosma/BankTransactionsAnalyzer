package com.fragmanos.web.controller;

import com.fragmanos.controller.TransactionController;
import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.dao.YearStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.database.model.MonthStat;
import com.fragmanos.directory.DirectoryReader;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BankController {

    @Autowired
    BankTransactionDao bankTransactionDao;

    @Autowired
    MonthStatDao monthStatDao;

    @Autowired
    YearStatDao yearStatDao;

    private static final Logger log = LoggerFactory.getLogger(BankController.class);

    @Value("${transactions.directory}")
    public String inputDirectory;

    @PostConstruct
    public void populateDatabase() {
        DirectoryReader directoryReader = new DirectoryReader();
        TransactionController transactionController = new TransactionController();

        if(!directoryReader.isDirectoryEmpty(inputDirectory)) {
            List<BankTransaction> bankTransactionsFromDirectory = getBankTransactionsFromDirectory(transactionController);
            for(BankTransaction bankTransaction : bankTransactionsFromDirectory) {
                bankTransactionDao.save(bankTransaction);
                setMonthStat(bankTransaction);
            }
        }
    }

    private List<BankTransaction> getBankTransactionsFromDirectory(TransactionController transactionController) {
        List<BankTransaction> bankTransactionsFromDirectory = new ArrayList<BankTransaction>();
        try {
            bankTransactionsFromDirectory = transactionController.getBankTransactionsFromDirectory(inputDirectory);
        } catch(ParseException e) {
            log.error("ParseException while parsing directory", e);
        } catch(IOException e) {
            log.error("IO exception while parsing directory", e);
        }
        return bankTransactionsFromDirectory;
    }

    @RequestMapping("/api/bank/allTransactions")
    public List<TableObject> fillTable() {
        List<TableObject> dataForTable = new ArrayList<TableObject>();
        List<BankTransaction> allBankTransactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
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
        return monthStatDao.findAllByOrderByYearMonthDesc();
    }

    @RequestMapping("/transaction")
    public List<BankTransaction> getBankTransactions() {
        return bankTransactionDao.findAllByOrderByTransactiondateDesc();
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
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
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
                // TODO increase the accuracy of the below calculations, avoid double -> string -> double
                monthStatForUpdate.setIncome(Double.parseDouble(decimalFormat.format(income + monthStatForUpdate.getIncome())));
                monthStatForUpdate.setExpense(Double.parseDouble(decimalFormat.format(expense + monthStatForUpdate.getExpense())));
                monthStatForUpdate.setProfit(Double.parseDouble(decimalFormat.format(profit + monthStatForUpdate.getProfit())));
                monthStatDao.save(monthStatForUpdate);
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