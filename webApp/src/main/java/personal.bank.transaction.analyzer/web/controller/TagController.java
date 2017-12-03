package personal.bank.transaction.analyzer.web.controller;

import personal.bank.transaction.analyzer.database.dao.BankTransactionDao;
import personal.bank.transaction.analyzer.database.dao.TagRuleDao;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.database.model.TagRule;
import personal.bank.transaction.analyzer.util.BankTransactionUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import personal.bank.transaction.analyzer.web.service.TagLoader;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/tags/")
public class TagController {

  private BankTransactionDao bankTransactionDao;
  private TagRuleDao tagRuleDao;
  private TagLoader tagLoader;
  private BankTransactionUtil util;

  @Inject
  public TagController(BankTransactionDao bankTransactionDao, TagRuleDao tagRuleDao, TagLoader tagLoader, BankTransactionUtil util) {
    this.bankTransactionDao = bankTransactionDao;
    this.tagRuleDao = tagRuleDao;
    this.tagLoader = tagLoader;
    this.util = util;
  }

  @RequestMapping("getPredefinedTags")
  public List<String> getAllTags() {
    return tagLoader.getTags();
  }

  @RequestMapping(value = "setTagsForTransaction")
  public
  @ResponseBody
  List<TableObject> setTagsForTransaction(@RequestBody TagRule tagRule) {
    List<BankTransaction> transactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    transactions.stream().filter(bankTransaction -> bankTransaction.getDescription().contains(tagRule.getDescription())).forEach(bankTransaction -> {
      tagRuleDao.save(tagRule);
      bankTransactionDao.save(bankTransaction.setTagRule(tagRule));
    });
    return util.getTableObjectList(transactions);
  }

  @RequestMapping(value = "getTaggedTransactionsCount")
  public long getTaggedTransactionsCount(){
    List<BankTransaction> all = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    return all.stream().filter(a -> a.containTags()).count();
  }

  @RequestMapping(value = "getUnTaggedTransactionsCount")
  public long getUnTaggedTransactionsCount(){
    List<BankTransaction> all = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    return all.stream().filter(a -> !a.containTags()).count();
  }

  @RequestMapping(value = "getTaggedTransactions")
  public List<TableObject> getTaggedTransactions(){
    List<BankTransaction> all = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    List<BankTransaction> bankTransactions = all.stream().filter(a -> a.containTags()).collect(Collectors.toList());
    return util.getTableObjectList(bankTransactions);
  }

  @RequestMapping(value = "getUnTaggedTransactions")
  public List<TableObject> getUnTaggedTransactions(){
    List<BankTransaction> all = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    List<BankTransaction> bankTransactions = all.stream().filter(a -> !a.containTags()).collect(Collectors.toList());
    return util.getTableObjectList(bankTransactions);
  }

}
