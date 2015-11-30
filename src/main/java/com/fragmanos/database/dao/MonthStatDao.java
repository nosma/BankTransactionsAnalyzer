package com.fragmanos.database.dao;

import java.util.List;

import com.fragmanos.database.model.MonthStat;
import org.joda.time.YearMonth;
import org.springframework.data.repository.CrudRepository;

/**
 * @author fragkakise on 30/11/2015.
 */
public interface MonthStatDao extends CrudRepository<MonthStat, Long> {
  List<MonthStat> findAll();

  MonthStat findByYearMonth(YearMonth yearMonth);
}
