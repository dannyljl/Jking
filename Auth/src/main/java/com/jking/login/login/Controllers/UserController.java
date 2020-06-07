package com.jking.login.login.Controllers;

import com.google.gson.Gson;
import com.jking.login.login.Repository.IUserRepository;
import com.jking.login.login.classes.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @PutMapping
    public User updateUser(@RequestBody User user){
        User savedUser = userRepository.save(user);
        savedUser.setPassword("");
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(exchange,routingkey,gson.toJson(user));
        return savedUser;
    }
}
