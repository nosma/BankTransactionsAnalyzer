//package com.fragmanos;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import com.fragmanos.AppConfig;
//import com.fragmanos.database.model.BankTransaction;
//import com.fragmanos.database.service.BankTransactionService;
//import org.joda.time.LocalDate;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * @author fragkakise on 11/09/2015.
// */
//@SpringBootApplication
//static class AppMain extends WebMvcConfigurerAdapter {
//
////  static static void main(String[] args) {
//////    SpringApplication.run(AppMain.class, args);
////    AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
////    BankTransactionService service = (BankTransactionService)context.getBean("bankTransactionService");
////
////    /*
////     * Create Transaction
////     */
////    BankTransaction bankTransaction1 = new BankTransaction();
////    bankTransaction1.setTransactiondate(new LocalDate(2010, 10, 10));
////    bankTransaction1.setDescription("My Bank Transaction Description");
////    bankTransaction1.setCost(100.15d);
////
////    /*
////     * Persist Transaction
////     */
////    service.saveBankTransaction(bankTransaction1);
////
////    /*
////     * Get All Transactions
////     */
////    List<BankTransaction> bankTransactionList = service.findAllBankTransactions();
////    for(BankTransaction transaction : bankTransactionList){
////      System.out.println(transaction);
////    }
////
////    /*
////     * Delete Transaction
////     */
//////    service.deleteBankTransactionByID(1);
////
////    /*
////     * Update Transaction
////     */
////    BankTransaction transaction = service.findBankTransactionByID(1);
////    transaction.setCost(200);
////    service.updateBankTransaction(transaction);
////
////        /*
////     * Get All Transactions
////     */
////    List<BankTransaction> bankTransactionList2 = service.findAllBankTransactions();
////    for(BankTransaction transaction2 : bankTransactionList){
////      System.out.println(transaction);
////    }
////
////
////  context.close();
////  }
////
////  private Date getNowDatetime() {
////    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
////    Date date = new Date();
////    return date;
////  }
//
//  static static void main(String[] args) {
//    ApplicationContext ctx = SpringApplication.run(AppConfig.class, args);
//
//    System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//    String[] beanNames = ctx.getBeanDefinitionNames();
//    Arrays.sort(beanNames);
//    for (String beanName : beanNames) {
//      System.out.println(beanName);
//    }
//  }
//
//}
