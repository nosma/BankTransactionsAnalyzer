package com.fragmanos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * @author fragkakise on 11/09/2015.
 */

public class TransactionTests {

  @Test
  public void insertTransaction(){

  }

  private Date getNowDatetime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return date;
  }

}
