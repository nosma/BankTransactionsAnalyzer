package personal.bank.transaction.analyzer.database.dao;

import personal.bank.transaction.analyzer.database.model.MonthStat;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;

public interface MonthStatDao extends CrudRepository<MonthStat, Long> {

  List<MonthStat> findAllByOrderByYearMonthDesc();

  MonthStat findByYearMonth(YearMonth yearMonth);
}
