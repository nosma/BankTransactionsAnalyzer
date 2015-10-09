package com.fragmanos;

import com.fragmanos.web.controller.BankController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

import static junit.framework.Assert.assertEquals;

/**
 * @author macuser on 9/27/15.
 */
@ContextConfiguration(classes = AppConfig.class)
public class DatabaseTests {

    private static final String FILENAME = "Financials";

    //TODO spring wiring must be fixed
    @Autowired
    private BankController bankController;

    @Test
    public void mvDatabaseFileCreated() {
        assertEquals(true, isMVDatabaseFileCreated());
    }

    @Test
    public void traceDatabaseFileCreated() {
        assertEquals(true, isTRACEDatabaseFileCreated());
    }

    @Test //(dependsOnMethods = "mvDatabaseFileCreated")
    public void insertTransactionsIntoDatabase() {
        bankController.populateDatabase();
        assertEquals(2, bankController.getBankTransactions().size());
    }


    private boolean isMVDatabaseFileCreated() {
        File f = new File(System.getProperty("user.dir") + "/db/" + FILENAME + ".mv.db");
        return (f.exists() && !f.isDirectory());
    }

    private boolean isTRACEDatabaseFileCreated() {
        File f = new File(System.getProperty("user.dir") + "/db/" + FILENAME + ".trace.db");
        return (f.exists() && !f.isDirectory());
    }
}
