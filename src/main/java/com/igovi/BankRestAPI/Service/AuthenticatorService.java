package com.igovi.BankRestAPI.Service;

import com.igovi.BankRestAPI.Model.User;
import com.igovi.BankRestAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {
    @Autowired
    private UserRepository userRepository;

    public User login(User user) {
        System.out.println("username = " + user.getUserName() + " password = " + user.getPassword());
        return userRepository.findByUserNameAndPassword(
                user.getUserName(),  user.getPassword());
    }
}
