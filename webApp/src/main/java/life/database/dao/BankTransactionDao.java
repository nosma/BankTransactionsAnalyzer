package life.database.dao;

import life.database.model.BankTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author fragkakise on 22/09/2015.
 */
public interface BankTransactionDao extends CrudRepository<BankTransaction, Long> {

  List<BankTransaction> findAllByOrderByTransactiondateDesc();

}
