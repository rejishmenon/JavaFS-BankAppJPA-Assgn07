package BankAppJPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BankAcct")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_id", unique = true, nullable = false)
    private int acct_id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private double balance;

    @Column(name = "pin")
    private int pin;

    public Account() {
    }

    public Account(String name, double balance, int pin) {
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }
    public int getAcct_id() {
        return acct_id;
    }

    public void setAcct_id(int acct_id) {
        this.acct_id = acct_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "acct_id=" + acct_id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", pin=" + pin +
                '}';
    }
}
