package com.fragmanos;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.web.controller.BankController;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;

/**
 * @author macuser on 9/27/15.
 */
@ContextConfiguration(classes = AppConfig.class)
public class DatabaseTests extends AbstractTestNGSpringContextTests {

    private static final String FILENAME = "Financials";

    @Autowired
    private BankController bankController;

    @Autowired
    BankTransactionDao bankTransactionDao;

    @Test
    public void mvDatabaseFileCreated() {
        assertEquals(true, isMVDatabaseFileCreated());
    }

    @Test (dependsOnMethods = "mvDatabaseFileCreated")
    public void insertTransactionsIntoDatabase() {
        populateDatabase();
        assertEquals(2, bankController.getBankTransactions().size());
    }

    private boolean isMVDatabaseFileCreated() {
        File f = new File(System.getProperty("user.dir") + "/db/" + FILENAME + ".mv.db");
        return (f.exists() && !f.isDirectory());
    }

    public void populateDatabase() {
        BankTransaction transaction1 = new BankTransaction(new LocalDate(),"Shoes",60d);
        BankTransaction transaction2 = new BankTransaction(new LocalDate(),"Hat",10d);
        bankTransactionDao.saveBankTransaction(transaction1);
        bankTransactionDao.saveBankTransaction(transaction2);
    }
}
