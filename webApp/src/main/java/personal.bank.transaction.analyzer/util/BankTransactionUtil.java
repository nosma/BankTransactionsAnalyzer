package personal.bank.transaction.analyzer.util;

import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Component;
import personal.bank.transaction.analyzer.database.model.BankTransaction;
import personal.bank.transaction.analyzer.web.controller.TableObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BankTransactionUtil {

  public List<BankTransaction> unionOfBankTransactions(List<BankTransaction> totalBankTransactionList, List<BankTransaction> fileBankTransactionList) {
    Set<BankTransaction> set = new HashSet<>();
    set.addAll(totalBankTransactionList);
    set.addAll(fileBankTransactionList);
    return new ArrayList<>(set);
  }

  public List<BankTransaction> differenceOfBankTransactions(List<BankTransaction> listA, List<BankTransaction> listB){
    return ListUtils.removeAll(listA, listB);
  }

   public List<TableObject> getTableObjectList(List<BankTransaction> bankTransactionList) {
        List<TableObject> tableObjectList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (BankTransaction bankTransaction : bankTransactionList) {
            tableObjectList.add(new TableObject(
                    bankTransaction.getTransactiondate().toString(),
                    bankTransaction.getDescription(),
                    bankTransaction.getCost(),
                    bankTransaction.getTags()
            ));
        }
        return tableObjectList;
    }
}
