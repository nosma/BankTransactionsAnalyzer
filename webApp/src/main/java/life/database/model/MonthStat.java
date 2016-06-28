package life.database.model;

import java.time.YearMonth;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author fragkakise on 30/11/2015.
 */
@Entity
@Table(name = "MONTH_STAT")
public class MonthStat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "YEARMONTH", nullable = false)
//  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentYearMonthAsString")
  private YearMonth yearMonth;

  @Column(name = "INCOME", nullable = false)
  private double income;

  @Column(name = "EXPENSE", nullable = false)
  private double expense;

  @Column(name = "PROFIT", nullable = false)
  private double profit;

  public MonthStat() {
  }

  public MonthStat(YearMonth yearMonth, double income, double expense, double profit) {
    this.yearMonth = yearMonth;
    this.income = income;
    this.expense = expense;
    this.profit = profit;
  }

  public YearMonth getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(YearMonth yearMonth) {
    this.yearMonth = yearMonth;
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

    MonthStat monthStat = (MonthStat)o;

    if(Double.compare(monthStat.income, income) != 0) return false;
    if(Double.compare(monthStat.expense, expense) != 0) return false;
    if(Double.compare(monthStat.profit, profit) != 0) return false;
    if(id != null ? !id.equals(monthStat.id) : monthStat.id != null) return false;
    return !(yearMonth != null ? !yearMonth.equals(monthStat.yearMonth) : monthStat.yearMonth != null);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = id != null ? id.hashCode() : 0;
    result = 31 * result + (yearMonth != null ? yearMonth.hashCode() : 0);
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
    return "MonthStat{" +
             "id=" + id +
             ", yearMonth=" + yearMonth +
             ", income=" + income +
             ", expense=" + expense +
             ", profit=" + profit +
             '}';
  }
}
