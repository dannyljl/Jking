package com.jking.login.login;

import Server.Filters.AuthenticationFilter;
import Server.Filters.AuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.persistence.MapKeyClass;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@Component
@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}
