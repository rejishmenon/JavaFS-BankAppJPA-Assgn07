package BankAppJPA.model;


import BankAppJPA.exceptions.AccountNotFoundException;
import jakarta.persistence.EntityManager;



public class GetAcctDetails {
    public static Account getAcctByID(EntityManager em, int acct_id) throws AccountNotFoundException {
//        Account acct = session.get(Account.class, acct_id);
//        return acct;
        return em.find(Account.class, acct_id);
    }
}


