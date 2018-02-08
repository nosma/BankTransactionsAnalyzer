package personal.bank.transaction.analyzer.web.controller;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.MonthStat;
import personal.bank.transaction.analyzer.database.model.TagRule;
import personal.bank.transaction.analyzer.web.service.BankStatisticsService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class StatisticServiceTests {

  private static final double DOUBLE_DELTA = 1e-15;

  private BankStatisticsService bankStatisticsService;

  private List<BankTransaction> bankTransactionList;
  private List<MonthStat> monthStaList;

  @BeforeMethod
  public void setUp() throws Exception {
    MonthStatDao monthStatDaoMock = mock(MonthStatDao.class);
    BankTransactionDao bankTransactionDaoMock = mock(BankTransactionDao.class);
    bankStatisticsService = new BankStatisticsService(monthStatDaoMock, bankTransactionDaoMock);

    bankTransactionList = new ArrayList<>();
    monthStaList = new ArrayList<>();

    when(bankTransactionDaoMock.findAllByOrderByTransactiondateDesc()).thenReturn(bankTransactionList);
    when(monthStatDaoMock.findAllByOrderByYearMonthDesc()).thenReturn(monthStaList);
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

  @Test
  public void testMonthlyTagsMatchExpected() {
    bankTransactionList.addAll(Arrays.asList(
        new BankTransaction(LocalDate.of(2018,1,1), "TFL", -1.5)
            .setTagRule(new TagRule("Bus", new ArrayList<>(Arrays.asList("Expenses", "Commute"))))
        ,new BankTransaction(LocalDate.of(2018,1,5), "TFL", -2.5)
        ,new BankTransaction(LocalDate.of(2018,1,11), "SAINTS", -20.0)
            .setTagRule(new TagRule("Foodmarket", new ArrayList<>(Arrays.asList("Expenses", "Food"))))
        ,new BankTransaction(LocalDate.of(2018,1,21), "TESCO", -30.0)
            .setTagRule(new TagRule("Foodmarket", new ArrayList<>(Arrays.asList("Expenses", "Food"))))
    ));
    final List<TagObject> monthlyTags = bankStatisticsService.getMonthlyExpenseTagsGroupedByTag(1, 2018);
    assertEquals(monthlyTags.size(), 4);
    assertEquals(monthlyTags.stream().filter(t -> t.getTagName().equals("Untagged")).findFirst().get().getAmount(), -2.5);
    assertEquals(monthlyTags.stream().filter(t -> t.getTagName().equals("Expenses")).findFirst().get().getAmount(), -51.5);
    assertEquals(monthlyTags.stream().filter(t -> t.getTagName().equals("Commute")).findFirst().get().getAmount(), -1.5);
    assertEquals(monthlyTags.stream().filter(t -> t.getTagName().equals("Food")).findFirst().get().getAmount(), -50.0);
  }

  private void setValuesForMedianTests() {
    monthStaList.add(new MonthStat(YearMonth.of(2015, 10), 4000.0, 1000.0, 2000.0));
    monthStaList.add(new MonthStat(YearMonth.of(2015, 11), 3000.0, 500.0, 2500.0));
    monthStaList.add(new MonthStat(YearMonth.of(2015, 12), 2000.0, 1500.0, 1500.0));
  }
}
