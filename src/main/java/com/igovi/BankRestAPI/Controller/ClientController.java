package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Model.ResponseMessage;
import com.igovi.BankRestAPI.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClient() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}" )
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            ResponseMessage responseMessage = new ResponseMessage("Client with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @PostMapping
    public Client createClient(@RequestBody Client Client) {
        return clientService.saveClient(Client);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client Client) {
        Client existingClient = clientService.checkClient(id);
        if (existingClient != null) {
            existingClient.setName(Client.getName());
            existingClient.setEmail(Client.getEmail());
            existingClient.setAge(Client.getAge());
            existingClient.setAccount_number(Client.getAccount_number());
            Client savedClient = clientService.saveClient(existingClient);
            return ResponseEntity.ok(savedClient);

        } else {
            ResponseMessage responseMessage = new ResponseMessage("Client with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Client existingClient = clientService.checkClient(id);
        if (existingClient != null) {
            clientService.deleteClient(id);
            ResponseMessage responseMessage = new ResponseMessage("Client with ID " + id + " deleted successfully.");
            return ResponseEntity.ok(responseMessage);
        }else{
            ResponseMessage responseMessage = new ResponseMessage("Client with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }
}
