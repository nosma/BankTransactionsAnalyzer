package personal.bank.transaction.analyzer.web.service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.MonthStat;
import personal.bank.transaction.analyzer.web.controller.BankStatisticsInterface;
import personal.bank.transaction.analyzer.web.controller.TagObject;

@Service
public class BankStatisticsService implements BankStatisticsInterface {

    private static final Logger log = LoggerFactory.getLogger(BankStatisticsService.class);
    private static final String UNTAGGED = "Untagged";

    @Value("${initial.balance}")
    private double initialBalance;

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
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
            totalIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
        }
        return totalIncome;
    }

    @Override
    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
            totalExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
        }
        return totalExpenses;
    }

    @Override
    public double getMonthlyIncome(int monthNumber, int yearNumber) {
        double monthlyIncome = 0;
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
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
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
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
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyExpenses += (bankTransaction.getCost() < 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyExpenses;
    }

    @Override
    public double getYearlyIncome(int yearNumber) {
        double yearlyIncome = 0;
        for (BankTransaction bankTransaction : getOrderedByDateTrasanctions()) {
            if (bankTransaction.getTransactiondate().getYear() == yearNumber) {
                yearlyIncome += (bankTransaction.getCost() > 0 ? bankTransaction.getCost() : 0);
            }
        }
        return yearlyIncome;
    }

    @Override
    public List<MonthStat> getMonthlyStatistics() {
        return getMonthStatsOrderByYearMonthDesc();
    }

    @Override
    public double getMedianMonthlyExpense() {
        List<MonthStat> monthStats = getMonthStatsOrderByYearMonthDesc();
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
        List<MonthStat> monthStats = getMonthStatsOrderByYearMonthDesc();
        double medianList[] = new double[monthStats.size()];
        int counter = 0;

        for (MonthStat monthStat : monthStats) {
            medianList[counter] = monthStat.getIncome();
            counter++;
        }

        Median median = new Median();
        return median.evaluate(medianList);
    }

    private List<BankTransaction> getOrderedByDateTrasanctions() {
        List<BankTransaction> transactions = Collections.emptyList();
        try {
         transactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
        } catch(Exception exception) {
            log.warn("BankTransactions database table is empty. ", exception);
        }
        return transactions;
    }

    private List<MonthStat> getMonthStatsOrderByYearMonthDesc() {
        List<MonthStat> monthStats = Collections.emptyList();
        try {
            monthStats = monthStatDao.findAllByOrderByYearMonthDesc();
        } catch(Exception exception) {
            log.warn("MonthStats database table is empty. ", exception);
        }
        return monthStats;
    }

    private List<TagObject> getMonthlyTagsGroupedByTag(List<BankTransaction> transactions) {
      Map<String, Double> tagObjects = new HashMap<>();
      for (BankTransaction transaction : transactions) {
        List<String> transactionTags = transaction.getTags();
        if (transactionTags.size() > 0) {
          transactionTags.forEach(transactionTag -> {
            Double tagValue = tagObjects.get(transactionTag);
            tagObjects.put(transactionTag, tagValue != null ? tagValue + transaction.getCost() : transaction.getCost());
          });
        } else {
          tagObjects.put(UNTAGGED, transaction.getCost());
        }
      }

      return tagObjects.entrySet().stream()
            .map(t -> new TagObject(t.getKey(), Double.valueOf(new DecimalFormat("#.##").format(t.getValue()))))
            .collect(Collectors.toList());
    }

  @Override
  public List<TagObject> getMonthlyIncomeTagsGroupedByTag(int month, int year) {
    List<BankTransaction> transactions = bankTransactionDao.findAllByOrderByTransactiondateDesc().stream()
       .filter(t -> t.getTransactiondate().getYear() == year)
       .filter(t -> t.getTransactiondate().getMonthValue() == month)
       .filter(t -> t.getCost() > 0)
       .collect(Collectors.toList());
    return getMonthlyTagsGroupedByTag(transactions);
  }

  @Override
  public List<TagObject> getMonthlyExpenseTagsGroupedByTag(int month, int year) {
    List<BankTransaction> transactions = bankTransactionDao.findAllByOrderByTransactiondateDesc().stream()
     .filter(t -> t.getTransactiondate().getYear() == year)
     .filter(t -> t.getTransactiondate().getMonthValue() == month)
     .filter(t -> t.getCost() < 0)
     .collect(Collectors.toList());
    return getMonthlyTagsGroupedByTag(transactions);
  }


}
