package BankAppJPA.service;


import BankAppJPA.exceptions.AccountNotFoundException;
import BankAppJPA.model.Account;
import jakarta.persistence.EntityManager;


public class GetAcctDetails {
    public static Account getAcctByID(EntityManager em, int acct_id) throws AccountNotFoundException {
        return em.find(Account.class, acct_id);
    }

}

