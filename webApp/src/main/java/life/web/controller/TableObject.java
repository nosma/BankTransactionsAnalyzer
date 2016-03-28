package life.web.controller;

public class TableObject {

  String date;
  String description;
  Double cost;

  public TableObject(String date, String description, Double cost) {
    this.date = date;
    this.description = description;
    this.cost = cost;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

}
