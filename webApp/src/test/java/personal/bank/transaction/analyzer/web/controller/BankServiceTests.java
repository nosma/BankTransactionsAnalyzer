package personal.bank.transaction.analyzer.web.controller;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MidataTransactionDao;
import personal.bank.transaction.analyzer.database.dao.MonthStatDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.TagRule;
import personal.bank.transaction.analyzer.web.service.BankService;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BankServiceTests {

  private BankService bankService;
  private int month;
  private int year;
  private List<BankTransaction> bankTransactionList;

  @BeforeMethod
  public void setUp() throws Exception {
    MonthStatDao monthStatDaoMock = mock(MonthStatDao.class);
    BankTransactionDao bankTransactionDaoMock = mock(BankTransactionDao.class);
    MidataTransactionDao midataTransactionDaoMock = mock(MidataTransactionDao.class);
    bankService = new BankService(monthStatDaoMock, bankTransactionDaoMock, midataTransactionDaoMock);
    month = 10;
    year = 2015;
    bankTransactionList = new ArrayList<>();
    when(bankTransactionDaoMock.findAllByOrderByTransactiondateDesc()).thenReturn(bankTransactionList);
  }

  @Test
  public void testObjectReturnedMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(1, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyExpensesListMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(1, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyExpensesListWithPositiveCostMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 1), "TEST1", 1000.0));
    assertEquals(0, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyIncomeListMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 1), "TEST1", 1000.0));
    assertEquals(1, bankService.getMonthlyIncomeList(month, year).size());
  }

  @Test
  public void testMonthlyIncomeListWithNegativeCostMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(LocalDate.of(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(0, bankService.getMonthlyIncomeList(month, year).size());
  }

  @Test
  public void testMonthlyTagsMatchExpected() {
    bankTransactionList.addAll(Arrays.asList(
        new BankTransaction(LocalDate.now(), "TFL", 1.5)
            .setTagRule(new TagRule("Bus", new ArrayList<>(Arrays.asList("Expenses", "Commute"))))
        ,new BankTransaction(LocalDate.now(), "TFL", 2.5)
        ,new BankTransaction(LocalDate.now(), "SAINTS", 20.0)
            .setTagRule(new TagRule("Foodmarket", new ArrayList<>(Arrays.asList("Expenses", "Food"))))
        ,new BankTransaction(LocalDate.now(), "TESCO", 30.0)
            .setTagRule(new TagRule("Foodmarket", new ArrayList<>(Arrays.asList("Expenses", "Food"))))
      ));
    final List<TagObject> monthlyTags = bankService.getMonthlyTagsGroupedByTag(1, 2018);
    assertEquals(3, monthlyTags.size());
    assertEquals(50.0, monthlyTags.stream().filter(t -> t.getTagName().equals("Foodmarket")).findFirst().get().getAmount());
    assertEquals(2.5, monthlyTags.stream().filter(t -> t.getTagName().equals("Untagged")).findFirst().get().getAmount());
  }

}
