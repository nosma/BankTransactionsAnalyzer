package com.fragmanos;

import java.time.LocalDate;

import life.file.DateUtilsImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DateUtilsImplTest {

  private DateUtilsImpl dateUtils;

  @BeforeMethod
  public void setUp() throws Exception {
    dateUtils = new DateUtilsImpl();
  }

  @Test
  public void testConvertDashedTextToDate() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("2016-12-31");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

  @Test
  public void testConvertSlashedTextToDate() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("12/31/16");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

  @Test
  public void testExpectedDateFormat() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("12/31/2016");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void testUnexpectedCharacterOnDateFormat() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("12.31.2016");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testConvertSlashedTextToDateGivesIllegalArgumentException() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("312/31/16");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testConvertDashedTextToDateGivesIllegalArgumentException() throws Exception {
    LocalDate localDate = dateUtils.convertTextToDate("312-31-16");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    Assert.assertEquals(expected, localDate);
  }

}

