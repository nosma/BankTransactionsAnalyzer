package life.web.controller;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MonthStat;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankStatisticsService implements BankStatisticsInterface {

    @Value("${initial.balance}")
    public double initialBalance;

    private final MonthStatDao monthStatDao;
    private final BankTransactionDao bankTransactionDao;

    @Autowired
    public BankStatisticsService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao) {
        this.monthStatDao = monthStatDao;
        this.bankTransactionDao = bankTransactionDao;
    }

    public double getInitialBalance() {
        return this.initialBalance;
    }

    @Override
    public double getTotalIncome() {
        double totalIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            totalIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
        }
        return totalIncome;
    }

    @Override
    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            totalExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
        }
        return totalExpenses;
    }

    @Override
    public double getMonthlyIncome(int monthNumber, int yearNumber) {
        double monthlyIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber) &&
                    (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
                monthlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
            }
        }
        return monthlyIncome;
    }

    @Override
    public double getMonthlyExpenses(int monthNumber, int yearNumber) {
        double monthlyExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber) &&
                    (bankTransaction.getTransactiondate().getYear() == yearNumber)) {
                monthlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
            }
        }
        return monthlyExpenses;
    }

    @Override
    public double getYearlyExpenses(int yearNumber) {
        double yearlyExpenses = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyExpenses;
    }

    @Override
    public double getYearlyIncome(int yearNumber) {
        double yearlyIncome = 0;
        for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyIncome;
    }

    @Override
    public List<MonthStat> getMonthlyStatistics() {
            return monthStatDao.findAllByOrderByYearMonthDesc();
    }

    @Override
    public double getMedianMonthlyExpense() {
        List<MonthStat> monthStats = monthStatDao.findAll();
        double medianList[] = new double[monthStats.size()];
        int counter = 0;

        for (MonthStat monthStat : monthStats) {
            medianList[counter] = monthStat.getExpense();
            counter++;
        }

        Median median = new Median();
        return median.evaluate(medianList);
    }

    @Override
    public double getMedianMonthlyIncome() {
        List<MonthStat> monthStats = monthStatDao.findAll();
        double medianList[] = new double[monthStats.size()];
        int counter = 0;

        for (MonthStat monthStat : monthStats) {
            medianList[counter] = monthStat.getIncome();
            counter++;
        }

        Median median = new Median();
        return median.evaluate(medianList);
    }
}
