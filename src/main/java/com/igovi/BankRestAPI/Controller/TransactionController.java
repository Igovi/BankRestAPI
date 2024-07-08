package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Model.ResponseMessage;
import com.igovi.BankRestAPI.Model.Transaction;
import com.igovi.BankRestAPI.Service.ClientService;
import com.igovi.BankRestAPI.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;
    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactions( Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Transaction> transactions = transactionService.getAllTransactions(sortedPageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}" )
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        } else {
            ResponseMessage responseMessage = new ResponseMessage("Transaction with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @GetMapping("/client/{id}" )
    public ResponseEntity<?> getTransactionByClientId(@PathVariable Long id) {
        List<Transaction> transaction = transactionService.getAllTransactionByClientId(id);
        if (!transaction.isEmpty()) {
            return ResponseEntity.ok(transaction);
        } else {
            Client existingClient = clientService.checkClient(id);
            ResponseMessage responseMessage = new ResponseMessage("");
            if(existingClient != null){
                responseMessage.setMessage("Transactions for client ID " + id + " not found.");
            }else {
                responseMessage.setMessage("Client with ID"  + id +  "not found.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
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
            ResponseMessage responseMessage = new ResponseMessage("Transaction with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        Transaction existingTransaction = transactionService.checkTransaction(id);
        if (existingTransaction != null) {
            transactionService.deleteTransaction(id);
            ResponseMessage responseMessage = new ResponseMessage("Transaction with ID " + id + " deleted successfully.");
            return ResponseEntity.ok(responseMessage);
        }else{
            ResponseMessage responseMessage = new ResponseMessage("Transaction with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }
}
