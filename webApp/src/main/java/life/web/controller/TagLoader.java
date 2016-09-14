package life.web.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TagLoader {

  @Value("${transaction.tags}")
  public String transactionTags;

  private List<String> tags;
  @PostConstruct
  private void loadTagsToDatabase(){
    tags = Arrays.asList(transactionTags.split(","));
  }

  List<String> getTags() {
    return tags;
  }
}
