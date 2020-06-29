package io.mitch.authorizationserver.controller;

import com.google.gson.Gson;
import io.mitch.authorizationserver.dao.UsersDao;
import io.mitch.authorizationserver.entity.AngularUser;
import io.mitch.authorizationserver.entity.UsersEntity;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsersDao userRepository;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${jking.rabbitmq.exchange}")
    private String exchange;
    @Value("${jking.rabbitmq.routingkey}")
    private String routingkey;

    @PostMapping("/update")
    public UsersEntity updateUser(@RequestBody AngularUser user){
        Optional<UsersEntity> value = userRepository.findById(user.getId());
        if (!value.isPresent()){
            return null;
        }
        UsersEntity realUser = value.get();
        if (user.getFirstName() != null){
            realUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null){
            realUser.setLastName(user.getLastName());
        }
        if (user.getUsername() != null){
            realUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null){
            realUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        UsersEntity savedUser = userRepository.save(realUser);
        savedUser.setPassword("");
        rabbitTemplate.convertAndSend(exchange,routingkey,new AngularUser(savedUser));
        return savedUser;
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id){
        System.out.println("id =" + id);
        Optional<UsersEntity> value = userRepository.findById(id);
        if (!value.isPresent()){
            return false;
        }
        UsersEntity realUser = value.get();
        AngularUser newAngularUser = new AngularUser(realUser);
        newAngularUser.setDelete(true);
        rabbitTemplate.convertAndSend(exchange,routingkey,newAngularUser);
        userRepository.deleteById(newAngularUser.getId());
        return true;
    }
}
