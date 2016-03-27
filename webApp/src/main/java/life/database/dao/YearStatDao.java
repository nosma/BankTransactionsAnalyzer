package life.database.dao;

import life.database.model.YearStat;
import org.joda.time.Years;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author fragkakise on 30/11/2015.
 */
public interface YearStatDao extends CrudRepository<YearStat, Long> {
  List<YearStat> findAll();

  YearStat findByYear(Years year);
}
