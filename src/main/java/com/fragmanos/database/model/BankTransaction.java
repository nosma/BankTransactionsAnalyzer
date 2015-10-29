package com.fragmanos.database.model;

import java.io.Serializable;
import javax.persistence.*;

import org.joda.time.LocalDate;

/**
 * @author fragkakise on 11/09/2015.
 */
@Entity
@Table(name = "BANKTRANSACTION")
public class BankTransaction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private int id;

  @Column(name = "TRANSACTIONDATE", nullable = false)
  private LocalDate transactiondate;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COST", nullable = false)
  private double cost;

  public LocalDate getTransactiondate() {
    return transactiondate;
  }

  public void setTransactiondate(LocalDate transactiondate) {
    this.transactiondate = transactiondate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public BankTransaction() {
  }

  public BankTransaction(LocalDate transactiondate, String description, double cost) {
    this.transactiondate = transactiondate;
    this.description = description;
    this.cost = cost;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    BankTransaction cmpBankTransaction = (BankTransaction)o;

    if(transactiondate!=null ? transactiondate.equals(cmpBankTransaction.transactiondate) : cmpBankTransaction.transactiondate!=null ) return false;
    if(description!=null ? description.equals(cmpBankTransaction.description) : cmpBankTransaction.description!=null ) return false;
//    if(cost==0 ? cost == cmpBankTransaction.cost : cmpBankTransaction.cost!=0 ) return false;
    if(Double.compare(cmpBankTransaction.cost, cost) != 0) return false;
//    if(!transactiondate.equals(cmpBankTransaction.transactiondate)) return false;
//    return description.equals(cmpBankTransaction.description);
    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = transactiondate.hashCode();
    result = 31 * result + description.hashCode();
    temp = Double.doubleToLongBits(cost);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "BankTransaction{" +
             "transactiondate=" + transactiondate +
             ", description='" + description + '\'' +
             ", cost=" + cost +
             '}';
  }
}
