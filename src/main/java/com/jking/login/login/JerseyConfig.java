package com.jking.login.login;

import Server.Filters.AuthenticationFilter;
import Server.Filters.AuthorizationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(AuthenticationFilter.class);
        register(AuthorizationFilter.class);
    }
}
