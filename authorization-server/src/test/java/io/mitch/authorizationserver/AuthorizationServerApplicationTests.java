package io.mitch.authorizationserver;

import io.mitch.authorizationserver.dao.UsersDao;
import io.mitch.authorizationserver.entity.AuthoritiesEntity;
import io.mitch.authorizationserver.entity.TokenResponse;
import io.mitch.authorizationserver.entity.UsersEntity;
import io.mitch.authorizationserver.service.Authority;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class AuthorizationServerApplicationTests {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TestRestTemplate  restTemplate;

	@Autowired
	private UsersDao usersDao;
	@Test
	public void contextLoads() {

	}

	@Test
	public void signIn(){
		//Making the User
		HashSet<AuthoritiesEntity> set = new HashSet<>();
		UsersEntity user = new UsersEntity(bCryptPasswordEncoder.encode("TestPass21"),"TestUser21",set);
		set.add(new AuthoritiesEntity(user, Authority.ROLE_ADMIN));
		usersDao.save(user);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", "TestUser21");
		parameters.put("password", "TestPass21");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		UsersEntity test = usersDao.findByUsername("TestUser21");
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity("/users/login",parameters,TokenResponse.class);
		Assert.assertNotNull(response.getBody().getAccess_token());
	}

	@Test
	public void noToken(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Bearer " + "");
		ResponseEntity<String> response = restTemplate.getForEntity("/users/OK",String.class,headers);
		Assert.assertEquals((HttpStatus.UNAUTHORIZED),response.getStatusCode());
	}

	@Test
	public void adminAuthorities(){
		HashSet<AuthoritiesEntity> set = new HashSet<>();
		UsersEntity user = new UsersEntity(bCryptPasswordEncoder.encode("AdminPass21"),"AdminUser21",set);
		set.add(new AuthoritiesEntity(user, Authority.ROLE_ADMIN));
		usersDao.save(user);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", "AdminUser21");
		parameters.put("password", "AdminPass21");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		ResponseEntity<TokenResponse> response = restTemplate.postForEntity("/users/login",parameters,TokenResponse.class);
		UsersEntity testing = usersDao.findByUsername("AdminUser21");
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " + response.getBody().getAccess_token());
		HttpEntity entity = new HttpEntity(headers2);
		ResponseEntity<String> response2 = restTemplate.exchange("/users/OKSecured",HttpMethod.GET,entity,String.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED,response2.getStatusCode());


	}

	@Test
	public void adminAuthoritiesUnauthorized(){
		HashSet<AuthoritiesEntity> set = new HashSet<>();
		UsersEntity user = new UsersEntity(bCryptPasswordEncoder.encode("TestPass23"),"TestUser23",set);
		set.add(new AuthoritiesEntity(user, Authority.ROLE_USER));
		usersDao.save(user);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", "TestUser23");
		parameters.put("password", "TestPass23");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity("/users/login",parameters,TokenResponse.class);
		UsersEntity ent = usersDao.findByUsername("TestUser23");
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " + response.getBody().getAccess_token());
		HttpEntity entity = new HttpEntity(headers2);
		ResponseEntity<String> response2 = restTemplate.exchange("/users/OKSecured",HttpMethod.GET,entity,String.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED,response2.getStatusCode());


	}

	@Test
	public void userAuthorities(){
		HashSet<AuthoritiesEntity> set = new HashSet<>();
		UsersEntity user = new UsersEntity(bCryptPasswordEncoder.encode("TestPass22"),"TestUser22",set);
		set.add(new AuthoritiesEntity(user, Authority.ROLE_USER));
		usersDao.save(user);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", "TestUser22");
		parameters.put("password", "TestPass22");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity("/users/login",parameters,TokenResponse.class);

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " + response.getBody().getAccess_token());
		HttpEntity entity = new HttpEntity(headers2);
		ResponseEntity<String> response2 = restTemplate.exchange("/users/okuser",HttpMethod.GET,entity,String.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED,response2.getStatusCode());

	}

}
