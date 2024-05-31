package com.igovi.BankRestAPI.Service;

import com.igovi.BankRestAPI.Model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractService {

    public double sumTotalTransactions(List<Transaction> transactions){
        double total = 0;

        for (Transaction transaction : transactions) {
            if(transaction.getType().equals("debit")){
                total += transaction.getAmount();
            }else{
                total -= transaction.getAmount();
            }
        }

        return total;
    }

}
