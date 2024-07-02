package com.igovi.BankRestAPI.Controller;

import com.igovi.BankRestAPI.Model.User;
import com.igovi.BankRestAPI.Service.AuthenticatorService;
import com.igovi.BankRestAPI.Util.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("autenticacao")
public class AuthenticadorController {

    @Autowired
    private AuthenticatorService authService;

    @PostMapping("login")  // http://localhost:8080/autenticacao/login
    public TokenResponse login(@RequestBody User user) {
        User userLogged = authService.login(user);
        if (userLogged != null) {
            return new TokenResponse("Sucesso");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ou credenciais inválidas");
        }
    }
}
