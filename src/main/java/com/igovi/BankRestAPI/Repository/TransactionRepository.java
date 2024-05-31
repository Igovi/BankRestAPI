package com.igovi.BankRestAPI.Repository;

import com.igovi.BankRestAPI.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<com.igovi.BankRestAPI.Model.Transaction> findAllByClientId(Long clientId);
}
