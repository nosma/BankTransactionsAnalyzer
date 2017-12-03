package personal.bank.transaction.analyzer.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class TagLoader {

  @Value("${transaction.tags}")
  public String transactionTags;

  private List<String> tags;
  @PostConstruct
  private void loadTagsToDatabase(){
    tags = Arrays.asList(transactionTags.split(","));
  }

  public List<String> getTags() {
    return tags;
  }
}
