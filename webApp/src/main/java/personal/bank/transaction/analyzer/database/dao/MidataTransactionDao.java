package personal.bank.transaction.analyzer.database.dao;

import personal.bank.transaction.analyzer.database.model.MidataTransaction;
import org.springframework.data.repository.CrudRepository;

public interface MidataTransactionDao extends CrudRepository<MidataTransaction, Long>{
}
