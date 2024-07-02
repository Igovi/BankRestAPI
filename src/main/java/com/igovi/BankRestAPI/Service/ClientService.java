package com.igovi.BankRestAPI.Service;

import com.igovi.BankRestAPI.Model.Client;
import com.igovi.BankRestAPI.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client checkClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client Client) {
        return clientRepository.save(Client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

