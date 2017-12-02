package personal.bank.transaction.analyzer.database.dao;

import personal.bank.transaction.analyzer.database.model.TagRule;
import org.springframework.data.repository.CrudRepository;

public interface TagRuleDao extends CrudRepository<TagRule, Long> {
}
