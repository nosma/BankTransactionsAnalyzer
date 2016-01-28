package com.fragmanos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/bank/")
public class BankController {

    @Autowired
    private BankInterface bankInterface;

    @PostConstruct
    @RequestMapping("populateDatabase")
    public void populateDatabase() {
        bankInterface.populateDatabase();
    }

    @RequestMapping("allTransactions")
    //todo the name of this method must change and along the implementation of it, probably a transformer of type should be added
    public List<TableObject> getTableObjects() {
        return bankInterface.getTableObjects();
    }

}