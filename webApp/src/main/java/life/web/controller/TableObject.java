package life.web.controller;

import java.util.List;

public class TableObject {

  private String date;
  private String description;
  private Double cost;
  private List<String> tags;

  public TableObject(String date, String description, Double cost, List<String> tags) {
    this.date = date;
    this.description = description;
    this.cost = cost;
    this.tags = tags;
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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }
}
