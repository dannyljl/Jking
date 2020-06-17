package com.jking.login.login.Controllers;

import com.google.gson.Gson;
import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${jking.rabbitmq.exchange}")
    private String exchange;
    @Value("${jking.rabbitmq.routingkey}")
    private String routingkey;

    @PostMapping
    public User updateUser(@RequestBody User user){
        User savedUser = userRepository.save(user);
        savedUser.setPassword("");
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(exchange,routingkey,gson.toJson(user));
        return savedUser;
    }
}
