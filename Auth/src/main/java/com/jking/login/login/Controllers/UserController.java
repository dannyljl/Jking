package com.jking.login.login.Controllers;

import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @PutMapping
    public User updateUser(@RequestBody User user){
         return userRepository.save(user);
    }
}
