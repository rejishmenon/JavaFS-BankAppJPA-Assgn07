package BankAppJPA.service;

import BankAppJPA.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.SQLException;

public class UpdateAccount {

    public static void updateAcctDB(EntityManager em, Account account) {
        EntityTransaction etx = em.getTransaction();

        try{
            etx.begin();
            em.persist(account);
            System.out.println("Account updated Successfully...");
            etx.commit();
        }catch (Exception e){
            etx.rollback();
            System.out.println("Balance Update error error ;" + e.getMessage());
        }
    }
    public static void updateAcctMultiDB(EntityManager em, Account account, Account xfrto) {
        EntityTransaction etx = em.getTransaction();
        try{
            etx.begin();
            em.persist(account);
            em.persist(xfrto);
            System.out.println("Transfer Successful...");
            etx.commit();
        }catch (Exception e){
            etx.rollback();
            System.out.println("Balance Update error error ;" + e.getMessage());
        }
    }
}
