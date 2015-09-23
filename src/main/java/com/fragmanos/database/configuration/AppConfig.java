package com.fragmanos.database.configuration;

import java.util.List;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.model.BankTransaction;
import org.joda.time.LocalDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author fragkakise on 22/09/2015.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.fragmanos.database.dao, com.fragmanos.database.service" })
@PropertySource(value = { "classpath:application.properties" })
@EntityScan(basePackages = "com.fragmanos.database.model")
public class AppConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(AppConfig.class, args);

//    System.out.println("Let's inspect the beans provided by Spring Boot:");
//    String[] beanNames = ctx.getBeanDefinitionNames();
//    Arrays.sort(beanNames);
//    for (String beanName : beanNames) {
//      System.out.println(beanName);
//    }

    // create a database entry
    BankTransactionDao bankTransactionDao = ctx.getBean(BankTransactionDao.class);
    BankTransaction transaction1 = new BankTransaction(new LocalDate(),"Shoes",60d);
    BankTransaction transaction2 = new BankTransaction(new LocalDate(),"Hat",10d);
    bankTransactionDao.saveBankTransaction(transaction1);
    bankTransactionDao.saveBankTransaction(transaction2);

    // select all from database
    List<BankTransaction> allBankTransactions = bankTransactionDao.findAllBankTransactions();
    for(BankTransaction myTransaction : allBankTransactions){
      printTransactionStatement(myTransaction);
    }

  }

  private static void printTransactionStatement(BankTransaction myTransaction) {
    StringBuilder sb = new StringBuilder("");
    String line = "\n===========================";
    sb.append("\nTransaction")
      .append(line)
      .append("\nDate: ").append(myTransaction.getTransactiondate())
      .append("\nDescription: ").append(myTransaction.getDescription())
      .append("\nCost: ").append(myTransaction.getCost())
      .append(line);
    System.out.println(sb);
  }

}
