package com.fragmanos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import life.database.dao.BankTransactionDao;
import life.database.dao.MidataTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.web.controller.BankService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
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

}
