package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Model.Transaction;
import com.igovi.BankRestAPI.Service.ClientService;
import com.igovi.BankRestAPI.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}" )
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        } else {
            String errorMessage = "Transaction with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/client/{id}" )
    public ResponseEntity<?> getTransactionByClientId(@PathVariable Long id) {
        List<Transaction> transaction = transactionService.getAllTransactionByClientId(id);
        if (!transaction.isEmpty()) {
            return ResponseEntity.ok(transaction);
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

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction existingTransaction = transactionService.checkTransaction(id);
        if (existingTransaction != null) {
            existingTransaction.setClientId(transaction.getClientId());
            existingTransaction.setType(transaction.getType());
            existingTransaction.setAmount(transaction.getAmount());
            existingTransaction.setTransactionDate(transaction.getTransactionDate());
            Transaction savedTransaction = transactionService.saveTransaction(existingTransaction);
            return ResponseEntity.ok(savedTransaction);

        } else {
            String errorMessage = "transaction with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        Transaction existingTransaction = transactionService.checkTransaction(id);
        if (existingTransaction != null) {
            transactionService.deleteTransaction(id);
            return ResponseEntity.ok("Transaction with ID " + id + " deleted successfully.");
        }else{
            String errorMessage = "Transaction with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
