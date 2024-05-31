package com.igovi.BankRestAPI.Repository;

import com.igovi.BankRestAPI.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}