package org.example.domain;

public class BankAccount{

    private final String name;
    private double balance;

    public BankAccount(String name, double initialBalance){
        if (initialBalance < 0.0) {
            throw new IllegalArgumentException("The initial balance cannot be negative");
        }
        this.name = name;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount < 0.0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount < 0.0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Not enough money to withdraw");
        }
        balance -= amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getName(){
        return this.name;
    }
}
