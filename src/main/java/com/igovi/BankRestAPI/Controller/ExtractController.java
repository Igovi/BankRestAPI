package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Model.Extract;
import com.igovi.BankRestAPI.Model.Transaction;
import com.igovi.BankRestAPI.Service.ClientService;
import com.igovi.BankRestAPI.Service.ExtractService;
import com.igovi.BankRestAPI.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/extract")
public class ExtractController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ExtractService extractService;
    @GetMapping("/client/{id}" )
    public ResponseEntity<?> getTransactionByClientId(@PathVariable Long id) {
        Extract extract = new Extract();
        List<Transaction> transactions = transactionService.getAllTransactionByClientId(id);
        if (!transactions.isEmpty()) {
            extract.setTransactions(transactions);
            extract.setTotal(extractService.sumTotalTransactions(transactions));
            return ResponseEntity.ok(extract);
        } else {
            Client existingClient = clientService.checkClient(id);
            String errorMessage = "";
            if(existingClient != null){
                errorMessage = "Transactions for client ID " + id + " not found.";
            }else {
                errorMessage = "Client with ID " + id + " not found.";
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

}
