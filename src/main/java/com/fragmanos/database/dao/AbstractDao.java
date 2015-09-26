package com.fragmanos.database.dao;
 
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.fragmanos.database.model.BankTransaction;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public abstract class AbstractDao {
 
    @Autowired
    private EntityManager entityManager;

    public void persist(Object entity) {
      entityManager.persist(entity);
    }

    public List selectAll(){
      return entityManager.createQuery("from " + BankTransaction.class.getName()).getResultList();
    }

    public void delete(Object entity) {
      entityManager.remove(entity);
    }

  public void deleteById(int id){
    BankTransaction bankTransaction = entityManager.find(BankTransaction.class, id);
    if(bankTransaction!=null){
      entityManager.remove(bankTransaction);
    }
  }

//  static BankTransaction update(BankTransaction transaction){
//    return entityManager.
//  }
}