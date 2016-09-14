package com.fragmanos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import life.database.model.BankTransaction;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BankTransactionTagsTest {

  private static final String EXPENSE = "Expenses";
  private static final String COMMUTE = "Commute";
  private ArrayList<BankTransaction> bankTransactionList;

  @BeforeMethod
  public void setUp() throws Exception {
    bankTransactionList = new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>();
    tags.add("Rent");
    tags.add("Home");
    tags.add("Expenses");
    bankTransactionList.add(new BankTransaction(LocalDate.now(),"Payroll",1500.0).setTag("Payroll"));
    bankTransactionList.add(new BankTransaction(LocalDate.now(),"Underground",3.0).setTag(COMMUTE).setTag(EXPENSE));
    bankTransactionList.add(new BankTransaction(LocalDate.now(),"Overground",2.0).setTag(COMMUTE).setTag(EXPENSE));
    bankTransactionList.add(new BankTransaction(LocalDate.now(),"Supermarket",20.0));
    bankTransactionList.add(new BankTransaction(LocalDate.now(),"Rent",1000.0).setTags(tags));
  }

  @Test
  public void testNumberOfTransactionsThatHaveTags() throws Exception {
    assertEquals(4, bankTransactionList.stream().filter(a ->a.containTags()).count());
  }

  @Test
  public void testNumberOfTransactionsThatHaveNoTags() throws Exception {
    assertEquals(1, bankTransactionList.stream().filter(a -> !a.containTags()).count());
  }

  @Test
  public void testNumberOfTransactionsWithOneFilteredTag() throws Exception {
    assertEquals(3, bankTransactionList.stream().filter(a -> a.containTag(EXPENSE)).count());
  }

  @Test
  public void testNumberOfTransactionsWithMultipleFilteredTags() throws Exception {
    ArrayList<String> tags = new ArrayList<>();
    tags.add(EXPENSE);
    tags.add(COMMUTE);
    assertEquals(2, bankTransactionList.stream().filter(a -> a.containTags(tags)).count());
  }
}