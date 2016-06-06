package life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author fragkakise on 22/09/2015.
 * SpringBootApplication, adds the following
 * - Configuration
 * - EnableAutoConfiguration
 * - ComponentScan
 */
@SpringBootApplication

public class AppConfig extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(AppConfig.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(AppConfig.class, args);
  }

}
