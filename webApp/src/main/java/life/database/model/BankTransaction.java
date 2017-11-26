package life.database.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "BANKTRANSACTION"
,uniqueConstraints = @UniqueConstraint(columnNames = {"transactiondate","description","cost"})
)
public class BankTransaction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TRANSACTIONDATE", nullable = false)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
  private LocalDate transactiondate;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COST", nullable = false)
  private Double cost;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TAG_RULE_ID")
  @JsonFormat(shape=JsonFormat.Shape.OBJECT)
  private TagRule tagRule;

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

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public TagRule getTagRule() {
    return tagRule;
  }

  public BankTransaction setTagRule(TagRule tagRule) {
    this.tagRule = tagRule;
    return this;
  }

  public BankTransaction() {
  }

  public BankTransaction(LocalDate transactiondate, String description, Double cost) {
    this.transactiondate = transactiondate;
    this.description = description;
    this.cost = cost;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    if(o instanceof BankTransaction) {
      BankTransaction cmpBankTransaction = (BankTransaction)o;
      if((transactiondate != null) && (cmpBankTransaction.transactiondate != null) && transactiondate.equals(cmpBankTransaction.transactiondate)) {
        if((description != null) && (cmpBankTransaction.description != null) && description.equals(cmpBankTransaction.description)
                && (cost != null) && cmpBankTransaction.cost !=null && cost.equals(cmpBankTransaction.cost) ) {
           return true;
        }
      }
    }
    return false;
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
    return "BankTransaction { " +
             "transactiondate= " + transactiondate +
             ", description= '" + description + '\'' +
             ", cost= " + cost +
             '}';
  }

  public BankTransaction setTags(ArrayList<String> tags) {
    this.tagRule.getTags().addAll(tags);
    return this;
  }

  public List<String> getTags() {
    return (this.tagRule != null) ? this.tagRule.getTags() : Lists.newArrayList();
  }

  public boolean containTags() {
    boolean containTags = false;
    if(tagRule != null) {
      containTags = (this.tagRule.getTags().size() > 0);
    }
    return containTags;
  }

  public boolean containTag(String tag) {
    return this.tagRule.getTags().contains(tag);
  }

  public boolean containTags(ArrayList tags) {
    return this.tagRule.getTags().containsAll(tags);
  }

}
