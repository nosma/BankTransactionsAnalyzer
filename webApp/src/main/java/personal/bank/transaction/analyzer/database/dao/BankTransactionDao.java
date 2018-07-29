package personal.bank.transaction.analyzer.database.dao;

import java.time.LocalDate;
import java.util.List;

import personal.bank.transaction.analyzer.database.model.BankTransaction;
import org.springframework.data.repository.CrudRepository;

public interface BankTransactionDao extends CrudRepository<BankTransaction, Long> {

  List<BankTransaction> findAllByOrderByTransactiondateDesc();

  List<BankTransaction> findByTransactiondateAndDescriptionAndCost(LocalDate localDate, String description, Double cost);

}
