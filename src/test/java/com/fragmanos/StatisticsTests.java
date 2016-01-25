package com.fragmanos;

import com.fragmanos.controller.BankTransactionUtil;
import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import com.fragmanos.web.controller.BankStatisticsController;
import com.fragmanos.web.controller.BankStatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatisticsTests {

  private static final double DOUBLE_DELTA = 1e-15;

  private String INPUT_DIRECTORY;
  private DirectoryReader directoryReader;
  private BankTransactionUtil bankTransactionUtil;

  BankStatisticsService bankStatisticsService;

  @Autowired
  private BankStatisticsController bankStatisticsController;

  @Autowired
  private List<BankTransaction> bankTransactionsFromDirectory;

  @Autowired
  private MonthStatDao monthStatDao;

  @Autowired
  private BankTransactionDao bankTransactionDao;

  //TODO fix the failing tests based one the new application design
  @Before
  public void setUp() throws Exception {
    INPUT_DIRECTORY = System.getProperty("user.dir") + "/src/test/resources/testTransactions/";
    directoryReader = new DirectoryReader();
    bankTransactionUtil = new BankTransactionUtil();
    bankTransactionsFromDirectory = bankTransactionUtil.getBankTransactionsFromDirectory(INPUT_DIRECTORY);
    bankStatisticsService = new BankStatisticsService(monthStatDao,bankTransactionDao);
    bankStatisticsController = new BankStatisticsController(bankStatisticsService);
  }

  /* Total calculations */
  @Test
  public void testTotalIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, bankStatisticsController.getTotalIncome(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, bankStatisticsController.getTotalExpenses(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalProfitMatchesExpected() throws Exception {
    double profit = bankStatisticsController.getTotalIncome() + bankStatisticsController.getTotalExpenses();
    assertEquals(8953.0, profit, DOUBLE_DELTA);
  }

  /* Yearly calculations */
  @Test
  public void testYearlyIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, bankStatisticsController.getYearlyIncome(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, bankStatisticsController.getYearlyExpenses(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyProfitMatchesExpected() throws Exception {
    double yearlyProfit = bankStatisticsController.getYearlyIncome(2015) + bankStatisticsController.getYearlyExpenses(2015);
    assertEquals(8953.0, yearlyProfit, DOUBLE_DELTA);
  }

  /* Monthly calculations */
  @Test
  public void testMonthlyIncomeMatchesExpected() throws Exception {
    assertEquals(4096.0, bankStatisticsController.getMonthlyIncome(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyExpensesMatchesExpected() throws Exception {
    assertEquals(-1612.0, bankStatisticsController.getMonthlyExpenses(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyProfitMatchesExpected() throws Exception {
    double monthlyProfit = bankStatisticsController.getMonthlyIncome(10, 2015) + bankStatisticsController.getMonthlyExpenses(10, 2015);
    assertEquals(2484.0, monthlyProfit, DOUBLE_DELTA);
  }
}
