package com.igovi.BankRestAPI.Repository;

import com.igovi.BankRestAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);
}
