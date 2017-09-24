package com.fragmanos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import life.file.DateUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static life.file.parser.DatePattern.*;
import static org.testng.Assert.assertEquals;

public class DateUtilsTest {

  private final String statementDatePattern = STATEMENT_DATE_PATTERN.getDatePattern();
  private final String midataDatePattern = MIDATA_DATE_PATTERN.getDatePattern();
  private DateUtils dateUtils;
  private LocalDate expected;

  @BeforeMethod
  public void setUp() throws Exception {
    dateUtils = new DateUtils();
    expected = LocalDate.of(2016, 12, 31);
  }

  @Test
  public void testCorrectStatementDateFormat() throws Exception {
    assertEquals(expected, dateUtils.getLocalDate("2016-12-31", statementDatePattern));
  }

  @Test(expectedExceptions = DateTimeParseException.class)
  public void testIncorrectStatementDateFormat() throws Exception {
    assertEquals(expected, dateUtils.getLocalDate("31/12/2016", statementDatePattern));
  }

  @Test
  public void testCorrectMidataDateFormat() throws Exception {
    assertEquals(expected, dateUtils.getLocalDate("31/12/2016", midataDatePattern));
  }

  @Test(expectedExceptions = DateTimeParseException.class)
  public void testIncorrectMidataDateFormat() throws Exception {
    assertEquals(expected, dateUtils.getLocalDate("2016-12-31", midataDatePattern));
  }

}

