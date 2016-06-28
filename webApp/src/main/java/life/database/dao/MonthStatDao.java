package life.database.dao;

import life.database.model.MonthStat;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;

/**
 * @author fragkakise on 30/11/2015.
 */
public interface MonthStatDao extends CrudRepository<MonthStat, Long> {
  List<MonthStat> findAll();

  List<MonthStat> findAllByOrderByYearMonthDesc();

  MonthStat findByYearMonth(YearMonth yearMonth);
}
