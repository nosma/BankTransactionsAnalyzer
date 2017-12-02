package personal.bank.transaction.analyzer.database.converter;

import  javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, Date> {

  @Override
  public Date convertToDatabaseColumn(YearMonth yearMonth) {
    LocalDate localDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    return (yearMonth == null ? null : Date.valueOf(localDate));
  }

  @Override
  public YearMonth convertToEntityAttribute(Date sqlDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(sqlDate);
    YearMonth yearMonth = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    return (sqlDate == null ? null : yearMonth);
  }
}
