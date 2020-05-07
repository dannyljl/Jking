package com.jking.login.login.Controllers;
import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    @ResponseBody
    public User Register(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }
}
