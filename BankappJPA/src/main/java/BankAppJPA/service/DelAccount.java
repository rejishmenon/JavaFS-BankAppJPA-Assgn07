package BankAppJPA.service;

import BankAppJPA.exceptions.*;
import BankAppJPA.model.Account;
import jakarta.persistence.EntityManager;

public class DelAccount {
    public static void deleteAcctbyID(EntityManager em, Account acct) throws AccountDeletionException{
         em.remove(acct);
    }
}

