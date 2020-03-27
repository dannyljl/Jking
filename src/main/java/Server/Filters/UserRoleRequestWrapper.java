package Server.Filters;

import Server.AccountType;
import Server.Binding.Secured;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal() and isUserInRole().
 *  We supply these implementations here, where they are not normally populated unless we are going through
 *  the facility provided by the container.
 * <p>If he user or roles are null on this wrapper, the parent request is consulted to try to fetch what ever the container has set for us.
 * This is intended to be created and used by the UserRoleFilter.
 * @author thein
 *
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {


    String user;
    int role = -1;
    HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String user, int role, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.role = role;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        int roleId = Integer.parseInt(role);
        return this.role == roleId;
    }

    @Override
    public Principal getUserPrincipal() {
            return () -> user;
        }
    }
