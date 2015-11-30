package com.fragmanos;

import java.util.List;

import com.fragmanos.controller.TransactionController;
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
  private List<BankTransaction> bankTransactionsFromDirectory;

  @Before
  public void setUp() throws Exception {
    INPUT_DIRECTORY = System.getProperty("user.dir") + "\\src\\test\\resources\\testTransactions\\";
    directoryReader = new DirectoryReader();
    transactionController = new TransactionController();
    bankTransactionsFromDirectory = transactionController.getBankTransactionsFromDirectory(INPUT_DIRECTORY);
  }

  /* Total calculations */
  @Test
  public void testTotalIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, transactionController.getTotalIncome(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, transactionController.getTotalExpenses(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalProfitMatchesExpected() throws Exception {
    double profit = transactionController.getTotalIncome() + transactionController.getTotalExpenses();
    assertEquals(8953.0, profit, DOUBLE_DELTA);
  }

  /* Yearly calculations */
  @Test
  public void testYearlyIncomeMatchesExpected() throws Exception {
    assertEquals(12288.0, transactionController.getYearlyIncome(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyExpensesMatchesExpected() throws Exception {
    assertEquals(-3335.0, transactionController.getYearlyExpenses(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyProfitMatchesExpected() throws Exception {
    double yearlyProfit = transactionController.getYearlyIncome(2015) + transactionController.getYearlyExpenses(2015);
    assertEquals(8953.0, yearlyProfit, DOUBLE_DELTA);
  }

  /* Monthly calculations */
  @Test
  public void testMonthlyIncomeMatchesExpected() throws Exception {
    assertEquals(4096.0, transactionController.getMonthlyIncome(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyExpensesMatchesExpected() throws Exception {
    assertEquals(-1612.0, transactionController.getMonthlyExpenses(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyProfitMatchesExpected() throws Exception {
    double monthlyProfit = transactionController.getMonthlyIncome(10, 2015) + transactionController.getMonthlyExpenses(10, 2015);
    assertEquals(2484.0, monthlyProfit, DOUBLE_DELTA);
  }
}
