package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Model.Extract;
import com.igovi.BankRestAPI.Model.ResponseMessage;
import com.igovi.BankRestAPI.Model.Transaction;
import com.igovi.BankRestAPI.Service.ClientService;
import com.igovi.BankRestAPI.Service.ExtractService;
import com.igovi.BankRestAPI.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extract")
@CrossOrigin(origins = "*")
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
            ResponseMessage responseMessage = new ResponseMessage("");
            if(existingClient != null){
                responseMessage.setMessage("Transactions for client ID " + id + " not found.");
            }else {
                responseMessage.setMessage("Client with ID"  + id +  "not found.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

}
