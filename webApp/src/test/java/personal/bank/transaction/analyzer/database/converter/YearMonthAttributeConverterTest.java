package personal.bank.transaction.analyzer.database.converter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.time.YearMonth;

import static org.testng.Assert.*;

public class YearMonthAttributeConverterTest {

    private YearMonthAttributeConverter attributeConverter;
    private YearMonth yearMonth;
    private Date sqlDate;
    private long firstDayOfJanuary2018;

    @BeforeMethod
    public void setUp() {
        attributeConverter = new YearMonthAttributeConverter();
        yearMonth = YearMonth.of(2018, 1);
        firstDayOfJanuary2018 = 1514764800000L;
        sqlDate = new Date(firstDayOfJanuary2018);
    }

    @Test
    public void testConvertToDatabaseColumn() {
        Date date = attributeConverter.convertToDatabaseColumn(yearMonth);
        assertNotNull(date);
        assertEquals(date.getTime(), firstDayOfJanuary2018);
    }

    @Test
    public void testConvertToEntityAttribute() {
        YearMonth yMonth = attributeConverter.convertToEntityAttribute(sqlDate);
        assertNotNull(yMonth);
        assertEquals(yMonth, yearMonth);
    }
}