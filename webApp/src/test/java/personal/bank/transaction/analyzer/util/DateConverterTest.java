package personal.bank.transaction.analyzer.util;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class DateConverterTest {

  @Test
  public void testCorrectStatementDate() {
    LocalDate localDate = DateConverter.getStatementDate("2016-12-31");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    assertEquals(expected, localDate);
  }

  @Test(expectedExceptionsMessageRegExp = "Not able to parse the statement date 31/12/2016 for pattern given: yyyy-MM-dd")
  public void testWrongStatementDate() {
    LocalDate localDate = DateConverter.getStatementDate("31/12/2016");
    assertNull(localDate);
  }

  @Test
  public void testCorrectMidataDate() {
    LocalDate localDate = DateConverter.getMidataDate("31/12/2016");
    LocalDate expected = LocalDate.of(2016, 12, 31);
    assertEquals(expected, localDate);
  }

  @Test(expectedExceptionsMessageRegExp = "Not able to parse the midata date 31/12/2016 for pattern given: dd/MM/yyyy")
  public void testWrongMidataDate() {
    LocalDate localDate = DateConverter.getMidataDate("2016-12-31");
    assertNull(localDate);
  }


}

