package com.jking.login.login.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Testcontroller {

    @GetMapping()
    public String TestService(){
        return "connection yes";
    }
}
