package com.fragmanos;

import com.fragmanos.web.controller.BankController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author macuser on 9/27/15.
 */
@ContextConfiguration(classes = AppConfig.class)
public class DatabaseTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private BankController bankController;

    @Test
    public void insertTransactionsIntoDatabase() {
        assertEquals(36, bankController.getBankTransactions().size());
    }

}
