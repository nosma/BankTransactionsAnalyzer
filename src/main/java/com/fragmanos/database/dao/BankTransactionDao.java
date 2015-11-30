package com.fragmanos.database.dao;

import java.util.List;

import com.fragmanos.database.model.BankTransaction;
import org.springframework.data.repository.CrudRepository;

/**
 * @author fragkakise on 22/09/2015.
 */
public interface BankTransactionDao extends CrudRepository<BankTransaction, Long> {
  List<BankTransaction> findAll();
}
