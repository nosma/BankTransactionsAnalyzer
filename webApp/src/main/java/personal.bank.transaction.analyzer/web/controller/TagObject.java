package personal.bank.transaction.analyzer.web.controller;

public class TagObject {

  private String tagName;
  private double amount;

  public TagObject(String tagName, double amount) {
    this.tagName = tagName;
    this.amount = amount;
  }

  public String getTagName() {
    return tagName;
  }

  public double getAmount() {
    return amount;
  }
}
