package life.util;

import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.inject.Named;

import life.database.model.BankTransaction;
import life.web.controller.TableObject;
import org.apache.commons.collections.ListUtils;

@Named
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
                    bankTransaction.getCost()
            ));
        }
        return tableObjectList;
    }


}
