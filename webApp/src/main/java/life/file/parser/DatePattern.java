package life.file.parser;

public enum DatePattern {

  STATEMENT_DATE_PATTERN("yyyy-MM-dd"),
  MIDATA_DATE_PATTERN("dd/MM/yyyy");

  private String datePattern;

  DatePattern(String pattern) {
    datePattern = pattern;
  }

  public String getDatePattern() {
    return datePattern;
  }
}
