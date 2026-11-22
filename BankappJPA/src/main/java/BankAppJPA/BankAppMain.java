package BankAppJPA;

import BankAppJPA.exceptions.*;
import BankAppJPA.model.Account;
import BankAppJPA.service.AddNewAccount;
import BankAppJPA.service.DelAccount;
import BankAppJPA.service.GetAcctDetails;
import BankAppJPA.service.UpdateAccount;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BankAppMain {

    public static Scanner scanner = new Scanner((System.in));
    public static String str;
    static int choice;
    static boolean loop = true;
    static Boolean isValid;
    static Account primeacct;
    static int primeacct_id = 0;
    static String name = null;
    static double amt = 0;
    static int pin = 0;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankappJPA");
        try (EntityManager em = emf.createEntityManager()) {
            System.out.println("\n----------------------------------");
            System.out.println("Welcome to the Bank App!  ");
            System.out.println("What do you want to do?   ");
            System.out.println("1. Add new account. ");
            System.out.println("2. Delete account. ");
            System.out.println("3. Proceed to BankMenu");
            System.out.print("4. Exit     --->  ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addAccount(em);
                case 2:
                    delAccount(em);
                case 3:
                    primeMenu(em);
                default:
                    System.out.println("Invalid Choice...");
            }

        } finally {
            emf.close();
        }
    }

    public static void addAccount(EntityManager em) {
        System.out.print("Enter Account Holder Name :->  ");
        scanner.nextLine();
        name = scanner.nextLine();
        System.out.print("Enter initial Balance :->  ");
        amt = scanner.nextDouble();
        System.out.print("Enter 4 digit Pin  :->   ");
        pin = scanner.nextInt();
        Account account = new Account(name, amt, pin);

        try {
            AddNewAccount.addNewAccount(em, account);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Goodbye....");
        }
    }

    public static void delAccount(EntityManager em) {
        System.out.print("Enter account number to Delete :->  ");
        primeacct_id = scanner.nextInt();

        //Check whether account exists
        primeacct = GetAcctDetails.getAcctByID(em, primeacct_id);

        try {
            if (primeacct != null) {
                DelAccount.deleteAcctbyID(em, primeacct);
            }
        } catch (AccountDeletionException e) {
            loop = false;
            System.out.println(e.getMessage() + " Goodbye....");
        }
    }

    public static void primeMenu(EntityManager em) {
        System.out.println("\n----------------------------------");
        System.out.println("Welcome to the Bank App!  ");
        System.out.print("Please enter your account ID >> ");
        primeacct_id = scanner.nextInt();

        try {
            primeacct = GetAcctDetails.getAcctByID(em, primeacct_id);
            System.out.println(primeacct.toString());
        } catch (AccountNotFoundException e) {
            loop = false;
            System.out.println(e.getMessage() + " Goodbye....");
        }
        mainMenu(em, primeacct);
    }


    private static void mainMenu(EntityManager em, Account acct) {
        while (loop) {
            System.out.println("\n----------------------------------");
            System.out.println("Welcome " + acct.getName() + ", to the new BankApp!  ");
            System.out.println("Please select an option :");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer funds");
            System.out.println("5. Exit");
            System.out.print(">>> ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displaybalance(primeacct);
                    break;
                case 2:
                    deposit(em, primeacct);
                    break;
                case 3:
                    withdraw(em, primeacct);
                    break;
                case 4:
                    transferFunds(em, primeacct);
                    break;
                case 5:
                    System.out.println("Thank you for visiting, Goodbye !!!");
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input, retry : \n");
                    break;
            }
        }
    }

    public static void displaybalance(Account acct){
        System.out.println("Your current account balance is " + acct.getBalance());
    }

    public static void deposit(EntityManager em, Account acct){
        System.out.println("Your Current Account Balance = " + acct.getBalance());
        System.out.print("Enter amount you want to deposit.  :->   ");
        double amt = scanner.nextDouble();
        if (amt > 0){
            acct.setBalance(acct.getBalance() + amt);
            UpdateAccount.updateAcctDB(em, acct);
            System.out.println("Deposit successful. New account balance = "+acct.getBalance());
        }else {
            System.out.println("Invalid Amount entered...");
        }

    }

    public static void withdraw(EntityManager em, Account acct){
        System.out.println("Your Current Account Balance = " + acct.getBalance());
        System.out.print("Enter amount you want to Withdraw.  :->   ");
        double amt = scanner.nextDouble();

        if (acct.getBalance() >= amt){
            acct.setBalance(acct.getBalance() - amt);
            UpdateAccount.updateAcctDB(em, acct);
            System.out.println("Withdraw successful. New account balance = "+acct.getBalance());
        }else {
            System.out.println("Invalid Amount entered...");
        }
    }

    public static void transferFunds(EntityManager em, Account acct){

        System.out.println("Your Current Account Balance = " + acct.getBalance());
        System.out.println("");
        System.out.print("Enter account id of the receipient  :->   ");
        Account transferToAcct=GetAcctDetails.getAcctByID(em, scanner.nextInt());;
        if (transferToAcct != null){
            System.out.print("Enter amount you want to transfered  :->   ");
            double amt = scanner.nextDouble();

            if (acct.getBalance() >= amt){
                acct.setBalance(acct.getBalance() - amt);
                transferToAcct.setBalance(transferToAcct.getBalance() + amt);
                UpdateAccount.updateAcctMultiDB(em,acct,transferToAcct);
                System.out.println("Withdraw successful. New account balance = "+acct.getBalance());
            }else {
                System.out.println("Invalid Amount entered...");
            }
        }else {
            System.out.println("Invalid Account...");
        }
    }



}