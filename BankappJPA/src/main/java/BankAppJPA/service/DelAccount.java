package BankAppJPA.model;

import BankAppJPA.exceptions.*;
import jakarta.persistence.EntityManager;

public class DelAccount {
    public static void deleteAcctbyID(EntityManager em, Account acct) throws AccountDeletionException{
//        Account acct = session.get(Account.class, acct_id);
//        return acct;
         em.remove(acct);
    }
}

