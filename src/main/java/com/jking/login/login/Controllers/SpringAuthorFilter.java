package com.jking.login.login.Controllers;

import Exceptions.UnauthorizedException;
import Server.AccountType;
import Server.Binding.Secured;
import org.springframework.core.annotation.Order;
import org.springframework.data.type.MethodsMetadata;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Order(2)
public class SpringAuthorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        System.out.println(
                "Logging Request  {} : {}" + req.getMethod() +
                        req.getRequestURI());
        System.out.println(
                "Logging Response :{}" +
                        res.getContentType());


        try {
                checkPermissions(req, extractRoles(req.getClass().getDeclaredAnnotation(Secured.class)));
                filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            throw new UnauthorizedException("User with ID '" + "' attempted to make an unauthorized request.");
        }
    }

    // Extract the roles from the annotated element
    private List<AccountType> extractRoles(Secured annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            if (annotatedElement == null) {
                return new ArrayList<>();
            } else {
                AccountType[] allowedRoles = annotatedElement.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(HttpServletRequest requestContext, List<AccountType> allowedRoles) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
        boolean permitted = allowedRoles == null;

            for (AccountType role : allowedRoles){
                if (requestContext.isUserInRole(Integer.toString(role.ordinal())))
                    permitted = true;
    }

        if(!permitted) {
            int userId = Integer.parseInt(requestContext.getUserPrincipal().getName());
            throw new UnauthorizedException("User with ID '" + userId + "' attempted to make an unauthorized request.");
        }
    }
}
