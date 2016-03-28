package life.database.model;

import org.joda.time.Years;

import javax.persistence.*;

/**
 * @author fragkakise on 30/11/2015.
 */
@Entity
@Table(name = "YEARSTAT")
public class YearStat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "YEAR", nullable = false)
  private Years year;

  @Column(name = "INCOME", nullable = false)
  private double income;

  @Column(name = "EXPENSE", nullable = false)
  private double expense;

  @Column(name = "PROFIT", nullable = false)
  private double profit;

  public YearStat(Years year, double income, double expense, double profit) {
    this.year = year;
    this.income = income;
    this.expense = expense;
    this.profit = profit;
  }

  public Years getYear() {
    return year;
  }

  public void setYear(Years year) {
    this.year = year;
  }

  public double getIncome() {
    return income;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public double getExpense() {
    return expense;
  }

  public void setExpense(double expense) {
    this.expense = expense;
  }

  public double getProfit() {
    return profit;
  }

  public void setProfit(double profit) {
    this.profit = profit;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    YearStat yearStat = (YearStat)o;

    if(Double.compare(yearStat.income, income) != 0) return false;
    if(Double.compare(yearStat.expense, expense) != 0) return false;
    if(Double.compare(yearStat.profit, profit) != 0) return false;
    if(id != null ? !id.equals(yearStat.id) : yearStat.id != null) return false;
    return !(year != null ? !year.equals(yearStat.year) : yearStat.year != null);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = id != null ? id.hashCode() : 0;
    result = 31 * result + (year != null ? year.hashCode() : 0);
    temp = Double.doubleToLongBits(income);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(expense);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(profit);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "YearStat{" +
             "id=" + id +
             ", year=" + year +
             ", income=" + income +
             ", expense=" + expense +
             ", profit=" + profit +
             '}';
  }
}
