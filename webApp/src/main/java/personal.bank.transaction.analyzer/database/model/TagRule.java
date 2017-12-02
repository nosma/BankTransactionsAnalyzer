package personal.bank.transaction.analyzer.database.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "TAG_RULE")
public class TagRule implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "TAGS", nullable = false)
  @ElementCollection
  private List<String> tags;

  public TagRule() {
  }

  public TagRule(String description, List<String> tags) {
    this.description = description;
    this.tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    TagRule tagRule = (TagRule)o;

    if(id != null ? !id.equals(tagRule.id) : tagRule.id != null) return false;
    if(description != null ? !description.equals(tagRule.description) : tagRule.description != null) return false;
    return tags != null ? tags.equals(tagRule.tags) : tagRule.tags == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (tags != null ? tags.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TagRule{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", tags=" + tags +
               '}';
  }
}
