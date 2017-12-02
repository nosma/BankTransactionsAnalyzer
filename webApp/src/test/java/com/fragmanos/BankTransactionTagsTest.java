package com.fragmanos;

import java.time.LocalDate;
import java.util.ArrayList;

import personal.bank.transaction.analyzer.database.dao.TagRuleDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.TagRule;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BankTransactionTagsTest {

  private static final String EXPENSE = "Expenses";
  private static final String COMMUTE = "Commute";
  private ArrayList<BankTransaction> bankTransactionList;
  @Mock
  private TagRuleDao tagRuleDao;

  @BeforeClass
  public void setUp() throws Exception {
    tagRuleDao = mock(TagRuleDao.class);

    bankTransactionList = new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>();
    tags.add("Rent");
    tags.add("Home");
    tags.add("Expenses");
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "Payroll", 1500.0).setTagRule(new TagRule("Payroll", Lists.newArrayList("Income", "Payroll"))));
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "Underground", 3.0).setTagRule(new TagRule("Commute", Lists.newArrayList("Expenses", "Commute"))));
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "Overground", 2.0).setTagRule(new TagRule("Commute", Lists.newArrayList("Expenses", "Commute"))));
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "Supermarket", 20.0).setTagRule(new TagRule("", Lists.newArrayList())));
    bankTransactionList.add(new BankTransaction(LocalDate.now(), "Rent", 1000.0).setTagRule(new TagRule("Expenses", tags)));

    when(tagRuleDao.findAll()).thenReturn(Lists.newArrayList(
        new TagRule("Payroll", Lists.newArrayList("Income","Payroll")),
        new TagRule("Commute", Lists.newArrayList("Expenses","Commute")),
        new TagRule("Commute", Lists.newArrayList("Expenses","Commute")),
        new TagRule("Expenses", Lists.newArrayList("Rent","Home","Expenses"))
        ));
  }

  @Test
  public void testNumberOfTransactionsThatHaveTags() throws Exception {
    assertEquals(4, bankTransactionList.stream().filter(a -> a.containTags()).count());
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