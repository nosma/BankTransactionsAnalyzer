package life.properties;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class PropertiesLoader implements PropertiesLoaderInterface {

  @Value("${transactions.directory}")
  public String inputDirectory;

  public String getInputDirectory() {
    return inputDirectory;
  }

}
