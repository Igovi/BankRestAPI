package com.igovi.BankRestAPI.Model;

import java.util.List;

public class Extract {

     private List<Transaction> transactions;

     private double total;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
