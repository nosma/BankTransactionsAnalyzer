package personal.bank.transaction.analyzer.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "MIDATATRANSACTION"
,uniqueConstraints = @UniqueConstraint(columnNames = {"date", "type", "description", "cost", "balance"})
)
public class MidataTransaction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "DATE", nullable = false)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
  private LocalDate date;

  @Column(name = "TYPE", nullable = false)
  private String type;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COST", nullable = false)
  private double cost;

  @Column(name = "BALANCE", nullable = false)
  private double balance;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public MidataTransaction() {
  }

  public MidataTransaction(LocalDate date, String type, String description, double cost, double balance) {
    this.date = date;
    this.type = type;
    this.description = description;
    this.cost = cost;
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    MidataTransaction that = (MidataTransaction)o;

    if(Double.compare(that.cost, cost) != 0) return false;
    if(Double.compare(that.balance, balance) != 0) return false;
    if(!date.equals(that.date)) return false;
    if(!type.equals(that.type)) return false;
    return description.equals(that.description);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = date.hashCode();
    result = 31 * result + type.hashCode();
    result = 31 * result + description.hashCode();
    temp = Double.doubleToLongBits(cost);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(balance);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "MidataTransaction{" +
               "date=" + date +
               ", type='" + type + '\'' +
               ", description='" + description + '\'' +
               ", cost=" + cost +
               ", balance=" + balance +
               '}';
  }
}
