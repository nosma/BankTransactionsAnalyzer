package com.fragmanos.properties;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;

@Named
@Singleton
public class PropertiesLoader implements PropertiesLoaderInterface {

  @Value("${transactions.directory}")
  public String inputDirectory;

  public String getInputDirectory() {
    return inputDirectory;
  }

}
