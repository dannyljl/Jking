package com.jking.login.login.Controllers;

import SecretJWTKey.constant;
import Server.AccountType;
import Server.Filters.UserRoleRequestWrapper;
import com.jking.login.login.Repository.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@Order(1)
public class SpringAuthenFilter implements Filter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private IUserRepository userRepository;


    public SpringAuthenFilter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // Get the Authorization header from the request
        String authorizationHeader =
                req.getHeader(HttpHeaders.AUTHORIZATION);
        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(servletResponse);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(servletResponse);
        }

        String subjectUserId = getSubjectUserId(token);
        int subjectRoleId = getSubjectRole(Integer.parseInt(subjectUserId)).ordinal();

        filterChain.doFilter(new UserRoleRequestWrapper(subjectUserId,subjectRoleId,req),servletResponse);

    }
    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ServletResponse response) throws IOException {

        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        if (userRepository.findBytoken(token) == null) {
            throw new Exception();
        }
    }

    private String getSubjectUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(constant.key)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private AccountType getSubjectRole(int id){
        long longId = id;
        return userRepository.findById(longId).get().getAccountType();
    }

}
