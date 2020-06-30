package io.mitch.authorizationserver.controller;


import com.google.gson.Gson;
import io.mitch.authorizationserver.dao.UsersDao;
import io.mitch.authorizationserver.entity.AngularUser;
import io.mitch.authorizationserver.entity.AuthoritiesEntity;
import Messages.UserModel;
import io.mitch.authorizationserver.entity.TokenResponse;
import io.mitch.authorizationserver.entity.UsersEntity;
import io.mitch.authorizationserver.service.Authority;
import org.apache.commons.codec.binary.Base64;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersDao applicationUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${jking.rabbitmq.exchange}")
    private String exchange;
    @Value("${jking.rabbitmq.routingkey}")
    private String routingkey;

    //signup
    @PostMapping("/sign-up")
    public UsersEntity signUp(@RequestBody AngularUser angularUser) {
        UsersEntity user = new UsersEntity(angularUser);
        System.out.println(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        HashSet<AuthoritiesEntity> set = new HashSet<>();
        set.add(new AuthoritiesEntity(user,Authority.ROLE_ADMIN));
        user.setAuthorities(set);
        user = applicationUserRepository.save(user);
        user.setPassword("");

        rabbitTemplate.convertAndSend(exchange,routingkey,new AngularUser(user));
        return user;
    }

    @PostMapping("/test-sign-up")
    public UsersEntity testSignUp(@RequestBody AngularUser angularUser) {
        UsersEntity user = new UsersEntity(angularUser);
        System.out.println(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        HashSet<AuthoritiesEntity> set = new HashSet<>();
        set.add(new AuthoritiesEntity(user,Authority.ROLE_ADMIN));
        user.setAuthorities(set);
        user = applicationUserRepository.save(user);
        user.setPassword("");
        return user;
    }

    @PostMapping("/login")
    public AngularUser login(@RequestBody AngularUser usersEntity){
        RestTemplate template = new RestTemplate();
        HttpHeaders header = createHeaders("clientId","client-secret");
        MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
        map.add("username",usersEntity.getUsername());
        map.add("password",usersEntity.getPassword());
        map.add("grant_type","password");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map,header);
        ResponseEntity<TokenResponse> response = template.postForEntity("http://jking-auth:8082/oauth/token",request,TokenResponse.class);
        UsersEntity entity = applicationUserRepository.findByUsername(usersEntity.getUsername());
        AngularUser angularUser = new AngularUser(entity,response.getBody().getAccess_token());
        return angularUser;
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestParam String refreshToken){
        RestTemplate template = new RestTemplate();
        HttpHeaders header = createHeaders("clientId","client-secret");
        MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
        map.add("refresh_token",refreshToken);
        map.add("grant_type","refresh_token");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map,header);
        ResponseEntity<TokenResponse> response = template.postForEntity("http://jking-auth:8082/oauth/token",request,TokenResponse.class);
        return response.getBody();
    }

    HttpHeaders createHeaders(String username, String password){
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",authHeader);
        return httpHeaders;
    }

    @GetMapping("/OK")
    public String OK(Principal principal){
        return principal.getName();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OKSecured")
    public String OKSec(Principal principal){
        return principal.getName();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/okuser")
    public String okadmin(Principal principal){
        return principal.getName();
    }


}
