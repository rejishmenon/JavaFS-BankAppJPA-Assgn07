package BankAppJPA.model;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class AddNewAccount {
    public static void addNewAccount(EntityManager em, Account account) throws SQLException {
        EntityTransaction etx = em.getTransaction();

        try{
            etx.begin();
            em.persist(account);
            System.out.println("Account added Successfully...");
            etx.commit();
        }catch (HibernateException e){
            etx.rollback();
            System.out.println("Save error ;" + e.getMessage());
        }
    }
}