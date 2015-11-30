package com.fragmanos;

import java.util.List;

import com.fragmanos.controller.TransactionController;
import com.fragmanos.controller.TransactionsUtil;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.directory.DirectoryReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author fragkakise on 30/11/2015.
 */
public class StatisticsTests {

  private static final double DOUBLE_DELTA = 1e-15;

  private static String INPUT_DIRECTORY;
  private DirectoryReader directoryReader;
  private TransactionController transactionController;
  private TransactionsUtil transactionsUtil;
  private List<BankTransaction> bankTransactionsFromDirectory;

  @Before
  public void setUp() throws Exception {
    INPUT_DIRECTORY = System.getProperty("user.dir") + "\\src\\test\\resources\\testTransactions\\";
    directoryReader = new DirectoryReader();
    transactionController = new TransactionController();
    bankTransactionsFromDirectory = transactionController.getBankTransactionsFromDirectory(INPUT_DIRECTORY);
    transactionsUtil = new TransactionsUtil(bankTransactionsFromDirectory);
  }

  /* Total calculations */
  @Test
  public void testTotalIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, transactionsUtil.getTotalIncome(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, transactionsUtil.getTotalExpenses(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalProfitMatchesExpected() throws Exception {
    double profit = transactionsUtil.getTotalIncome() + transactionsUtil.getTotalExpenses();
    assertEquals(8953.0, profit, DOUBLE_DELTA);
  }

  /* Yearly calculations */
  @Test
  public void testYearlyIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, transactionsUtil.getYearlyIncome(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, transactionsUtil.getYearlyExpenses(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyProfitMatchesExpected() throws Exception {
    double yearlyProfit = transactionsUtil.getYearlyIncome(2015) + transactionsUtil.getYearlyExpenses(2015);
    assertEquals(8953.0, yearlyProfit, DOUBLE_DELTA);
  }

  /* Monthly calculations */
  @Test
  public void testMonthlyIncomeMatchesExpected() throws Exception {
    assertEquals(4096.0, transactionsUtil.getMonthlyIncome(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyExpensesMatchesExpected() throws Exception {
    assertEquals(-1612.0, transactionsUtil.getMonthlyExpenses(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyProfitMatchesExpected() throws Exception {
    double monthlyProfit = transactionsUtil.getMonthlyIncome(10, 2015) + transactionsUtil.getMonthlyExpenses(10, 2015);
    assertEquals(2484.0, monthlyProfit, DOUBLE_DELTA);
  }
}
