package com.fragmanos;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MonthStat;
import life.web.controller.BankStatisticsService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticServiceTests {

  private static final double DOUBLE_DELTA = 1e-15;

  BankStatisticsService bankStatisticsService;

  private MonthStatDao monthStatDaoMock;
  private BankTransactionDao bankTransactionDaoMock;
  private List<BankTransaction> bankTransactionList;
  private List<MonthStat> monthStaList;

  @BeforeMethod
  public void setUp() throws Exception {
    monthStatDaoMock = mock(MonthStatDao.class);
    bankTransactionDaoMock = mock(BankTransactionDao.class);
    bankStatisticsService = new BankStatisticsService(monthStatDaoMock, bankTransactionDaoMock);

    bankTransactionList = new ArrayList<BankTransaction>();
    monthStaList = new ArrayList<MonthStat>();

    when(bankTransactionDaoMock.findAllByOrderByTransactiondateDesc()).thenReturn(bankTransactionList);
    when(monthStatDaoMock.findAllByOrderByYearMonthDesc()).thenReturn(monthStaList);
    when(monthStatDaoMock.findAll()).thenReturn(monthStaList);
  }

  /* Total calculations */
  @Test
  public void testTotalIncomeMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "TEST", 12288.0));
    assertEquals(12288.0, bankStatisticsService.getTotalIncome(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalExpensesMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "TEST", -3335.0));
    assertEquals(-3335.0, bankStatisticsService.getTotalExpenses(), DOUBLE_DELTA);
  }

  @Test
  public void testTotalProfitMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "TEST1", 12288.0));
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "TEST2", -3335.0));
    double profit = bankStatisticsService.getTotalIncome() + bankStatisticsService.getTotalExpenses();
    assertEquals(8953.0, profit, DOUBLE_DELTA);
  }

  /* Yearly calculations */
  @Test
  public void testYearlyIncomeMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 1, 1), "TEST", 12288.0));
    assertEquals(12288.0, bankStatisticsService.getYearlyIncome(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyExpensesMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 1, 1), "TEST", -3335.0));
    assertEquals(-3335.0, bankStatisticsService.getYearlyExpenses(2015), DOUBLE_DELTA);
  }

  @Test
  public void testYearlyProfitMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 1, 1), "TEST1", 12288.0));
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 2, 1), "TEST2", -3335.0));
    double yearlyProfit = bankStatisticsService.getYearlyIncome(2015) + bankStatisticsService.getYearlyExpenses(2015);
    assertEquals(8953.0, yearlyProfit, DOUBLE_DELTA);
  }

  /* Monthly calculations */
  @Test
  public void testMonthlyIncomeMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 31), "TEST", 4096.0));
    assertEquals(4096.0, bankStatisticsService.getMonthlyIncome(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyExpensesMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 31), "TEST", -1612.0));
    assertEquals(-1612.0, bankStatisticsService.getMonthlyExpenses(10, 2015), DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyProfitMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 31), "TEST1", 4096.0));
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 31), "TEST2", -1612.0));
    double monthlyProfit = bankStatisticsService.getMonthlyIncome(10, 2015) + bankStatisticsService.getMonthlyExpenses(10, 2015);
    assertEquals(2484.0, monthlyProfit, DOUBLE_DELTA);
  }

  @Test
  public void testMonthlyStatisticsMatchExpected() throws Exception {
    monthStaList.add(new MonthStat(YearMonth.now(),2000.0,1000.0,1000.0));
    monthStaList.add(new MonthStat(YearMonth.now(),3000.0,2000.0,1000.0));
    assertEquals(2, bankStatisticsService.getMonthlyStatistics().size());
  }

  @Test
  public void testMedianMonthlyExpenseMatchesExpected() throws Exception {
    setValuesForMedianTests();
    assertEquals(1000, bankStatisticsService.getMedianMonthlyExpense(), DOUBLE_DELTA);
  }

  @Test
  public void testMedianMonthlyIncomeMatchesExpected() throws Exception {
    setValuesForMedianTests();
    assertEquals(3000, bankStatisticsService.getMedianMonthlyIncome(), DOUBLE_DELTA);
  }

  private void setValuesForMedianTests() {
    monthStaList.add(new MonthStat(YearMonth.of(2015, 10), 4000.0, 1000.0, 2000.0));
    monthStaList.add(new MonthStat(YearMonth.of(2015, 11), 3000.0, 500.0, 2500.0));
    monthStaList.add(new MonthStat(YearMonth.of(2015, 12), 2000.0, 1500.0, 1500.0));
  }
}
