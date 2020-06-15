package com.jking.login.login.Controllers;

import SecretJWTKey.constant;
import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    IUserRepository userRepository;

    @GetMapping()
    public ResponseEntity<User> returnUser(Principal principal) {
        Optional<User> user = userRepository.findById(Long.parseLong(principal.getName()));
        user.get().setPassword("");
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }


    }
