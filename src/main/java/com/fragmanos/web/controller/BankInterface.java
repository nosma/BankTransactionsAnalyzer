package com.fragmanos.web.controller;

import java.util.List;

public interface BankInterface {
  List<TableObject> getTableObjects();

  void populateDatabase();
}
