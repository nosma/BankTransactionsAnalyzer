package personal.bank.transaction.analyzer.web.controller;

import java.util.ArrayList;

class TagDescription {

  private String description;
  private ArrayList<String> tags;

  public TagDescription() {
  }

  public TagDescription(String description, ArrayList<String> tags) {
    this.description = description;
    this.tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TagDescription that = (TagDescription) o;

    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    return tags != null ? tags.equals(that.tags) : that.tags == null;

  }

  @Override
  public int hashCode() {
    int result = description != null ? description.hashCode() : 0;
    result = 31 * result + (tags != null ? tags.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TagDescription{" +
        "description='" + description + '\'' +
        ", tags=" + tags +
        '}';
  }
}
