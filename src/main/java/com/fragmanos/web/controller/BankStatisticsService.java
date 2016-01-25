package com.fragmanos.web.controller;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.database.model.MonthStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankStatisticsService {

    private final MonthStatDao monthStatDao;

    private final BankTransactionDao bankTransactionDao;

    @Autowired
    public BankStatisticsService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao) {
        this.monthStatDao = monthStatDao;
        this.bankTransactionDao = bankTransactionDao;
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            totalIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
        }
        return totalIncome;
    }

    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            totalExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
        }
        return totalExpenses;
    }


    public double getMonthlyIncome(int monthNumber, int yearNumber) {
        double monthlyIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if ((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
                    (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
                monthlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
            }
        }
        return monthlyIncome;
    }

    public double getMonthlyExpenses(int monthNumber, int yearNumber) {
        double monthlyExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if ((bankTransaction.getTransactiondate().getMonthOfYear() == monthNumber) &&
                    (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
                monthlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
            }
        }
        return monthlyExpenses;
    }

    public double getYearlyExpenses(int yearNumber) {
        double yearlyExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyExpenses;
    }

    public double getYearlyIncome(int yearNumber) {
        double yearlyIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyIncome;
    }

    public List<MonthStat> getMonthlyStatistics() {
            return monthStatDao.findAllByOrderByYearMonthDesc();
    }
}
