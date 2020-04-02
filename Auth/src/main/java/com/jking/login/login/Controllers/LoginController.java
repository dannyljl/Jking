package com.jking.login.login.Controllers;

import SecretJWTKey.constant;
import Server.AccountType;
import Server.Binding.Secured;
import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> Login(@RequestBody User user) {
        User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (newUser != null) {
            String token =
                    Jwts.builder().setSubject(Long.toString(newUser.getId())).signWith(SignatureAlgorithm.HS256,
                            constant.key).compact();
            newUser.setToken(token);
            userRepository.save(newUser);
            return new ResponseEntity<>("Token : " + token + " logged in", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.OK);
        }
    }
    @PostMapping("/admin")
    public ResponseEntity<String> LoginAdmin(@RequestBody User user) {
        User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (newUser != null) {
            String token =
                    Jwts.builder().setSubject(Long.toString(newUser.getId())).signWith(SignatureAlgorithm.HS256,
                            constant.key).compact();
            newUser.setToken(token);
            userRepository.save(newUser);
            return new ResponseEntity<>("Token : " + token + " logged in", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.OK);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<String> LoginUser(@RequestBody User user) {
        User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (newUser != null) {
            String token =
                    Jwts.builder().setSubject(Long.toString(newUser.getId())).signWith(SignatureAlgorithm.HS256,
                            constant.key).compact();
            newUser.setToken(token);
            userRepository.save(newUser);
            return new ResponseEntity<>("Token : " + token + " logged in", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.OK);
        }
    }
}
