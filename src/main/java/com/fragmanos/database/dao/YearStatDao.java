package com.fragmanos.database.dao;

import java.util.List;

import com.fragmanos.database.model.YearStat;
import org.springframework.data.repository.CrudRepository;

/**
 * @author fragkakise on 30/11/2015.
 */
public interface YearStatDao extends CrudRepository<YearStat, Long> {
  List<YearStat> findAll();
}
