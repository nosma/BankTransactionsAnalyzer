package com.fragmanos;

import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.web.controller.BankController;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author macuser on 9/27/15.
 */
public class DatabaseTests {

    private static final String FILENAME = "Financials";

    @Autowired
    BankController bankController;

    @Test
    public void mvDatabaseFileCreated(){
        assertEquals(true, isMVDatabaseFileCreated());
    }

    @Test
    public void traceDatabaseFileCreated(){
        assertEquals(true, isTRACEDatabaseFileCreated());
    }

    @Test (dependsOnMethods = "mvDatabaseFileCreated")
    public void createDatabase(){
        List<BankTransaction> bankTransactions = bankController.getBankTransactions();
        assertEquals(2, bankTransactions.size());
    }


    private boolean isMVDatabaseFileCreated() {
        File f = new File(System.getProperty("user.dir")+"/db/"+FILENAME+".mv.db");
        return  (f.exists() && !f.isDirectory());
    }

    private boolean isTRACEDatabaseFileCreated() {
        File f = new File(System.getProperty("user.dir")+"/db/"+FILENAME+".trace.db");
        return  (f.exists() && !f.isDirectory());
    }
}
