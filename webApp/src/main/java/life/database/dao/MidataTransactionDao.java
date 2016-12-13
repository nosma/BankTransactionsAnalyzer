package life.database.dao;

import life.database.model.MidataTransaction;
import org.springframework.data.repository.CrudRepository;

public interface MidataTransactionDao extends CrudRepository<MidataTransaction, Long>{
}
