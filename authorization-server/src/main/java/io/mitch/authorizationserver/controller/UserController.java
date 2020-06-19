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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.HashSet;

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

    @PostMapping("/sign-up")
    public UsersEntity signUp(@RequestBody UsersEntity user) {
        System.out.println(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("not broken 1");
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        HashSet<AuthoritiesEntity> set = new HashSet<>();
        set.add(new AuthoritiesEntity(user,Authority.ROLE_ADMIN));
        user.setAuthorities(set);
        System.out.println("not broken 2");
        user = applicationUserRepository.save(user);
        System.out.println("not broken 3");
        user.setPassword("");
        System.out.println("probably breaks here");

        rabbitTemplate.convertAndSend(exchange,routingkey,new AngularUser(user));
        System.out.println("nop");
        return user;
    }

    @PostMapping("/login")
    public AngularUser login(@RequestBody UsersEntity usersEntity){
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
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/OK")
    public String OK(Principal principal){
        return principal.getName();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OKSecured")
    public String OKSec(Principal principal){
        return principal.getName();
    }

    @GetMapping("/findMe")
    public String findMe(Principal principal){
        return "";// ((Role)applicationUserRepository.findByUsername(principal.getName()).getRoles().toArray()[0]).getRoleName();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/okadmin")
    public String okadmin(Principal principal){
        return principal.getName();
    }


}