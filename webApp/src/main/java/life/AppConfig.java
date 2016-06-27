package life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fragkakise on 22/09/2015.
 * SpringBootApplication, adds the following
 * - Configuration
 * - EnableAutoConfiguration
 * - ComponentScan
 */
@SpringBootApplication

public class AppConfig  {

  public static void main(String[] args) {
    SpringApplication.run(AppConfig.class, args);
  }

}
