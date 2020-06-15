package com.jking.login.login;

import Server.AccountType;
import Server.Binding.Secured;
import com.jking.login.login.Controllers.SpringAuthenFilter;
import com.jking.login.login.Controllers.SpringAuthorFilter;
import com.jking.login.login.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

@Component
@SpringBootApplication
public class LoginApplication {

    @Autowired
    private IUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<SpringAuthenFilter> authenFilter() {
        FilterRegistrationBean<SpringAuthenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SpringAuthenFilter(userRepository));
        registrationBean.addUrlPatterns("/token");
        registrationBean.addUrlPatterns("/admin");
        registrationBean.addUrlPatterns("/authenticate");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SpringAuthorFilter> authorFilter() {
        FilterRegistrationBean<SpringAuthorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SpringAuthorFilter());
        registrationBean.addUrlPatterns("/admin");
        return registrationBean;
    }
}
