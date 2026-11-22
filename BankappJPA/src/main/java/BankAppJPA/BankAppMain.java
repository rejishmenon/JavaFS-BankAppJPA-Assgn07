package BankAppJPA;

import BankAppJPA.exceptions.*;
import BankAppJPA.model.Account;
import BankAppJPA.model.AddNewAccount;
import BankAppJPA.model.DelAccount;
import BankAppJPA.model.GetAcctDetails;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityExistsException;
import org.hibernate.Session;

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
        try(EntityManager em= emf.createEntityManager()) {
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

            }

        }finally {
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

        try{
            AddNewAccount.addNewAccount(em, account);
        }catch (SQLException e){
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
        }catch (AccountDeletionException e){
            loop=false;
            System.out.println(e.getMessage() + " Goodbye....");
        }
    }
    public static void primeMenu(EntityManager em){
        System.out.println("\n----------------------------------");
        System.out.println("Welcome to the Bank App!  ");
        System.out.print("Please enter your account ID >> ");
        primeacct_id = scanner.nextInt();

        try {
            primeacct= GetAcctDetails.getAcctByID(em,primeacct_id);
            System.out.println(primeacct.toString());
        } catch (AccountNotFoundException e) {
            loop=false;
            System.out.println(e.getMessage() + " Goodbye....");
        }
//        mainMenu(primeacct);
    }
}
