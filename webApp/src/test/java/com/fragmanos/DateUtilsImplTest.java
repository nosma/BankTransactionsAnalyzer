package com.fragmanos;

import life.file.DateUtilsImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class DateUtilsImplTest {

  private DateUtilsImpl dateUtils;

  @BeforeMethod
  public void setUp() throws Exception {
    dateUtils = new DateUtilsImpl();
  }

  @Test
  public void testCorrectStatementDate() throws Exception {
    LocalDate localDate = dateUtils.getStatementDate("2016-12-31");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    assertEquals(expected, localDate);
  }

  @Test(expectedExceptionsMessageRegExp = "Not able to parse the statement date 31/12/2016 for pattern given: yyyy-MM-dd")
  public void testWrongStatementDate() throws Exception {
    LocalDate localDate = dateUtils.getStatementDate("31/12/2016");
    assertEquals(null, localDate);
  }

  @Test
  public void testCorrectMidataDate() throws Exception {
    LocalDate localDate = dateUtils.getMidataDate("31/12/2016");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    assertEquals(expected, localDate);
  }

  @Test(expectedExceptionsMessageRegExp = "Not able to parse the midata date 31/12/2016 for pattern given: dd/MM/yyyy")
  public void testWrongMidataDate() throws Exception {
    LocalDate localDate = dateUtils.getMidataDate("2016-12-31");
    assertEquals(null, localDate);
  }


}

