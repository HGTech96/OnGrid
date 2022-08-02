package com.hg.dashboard.controller;


import com.hg.dashboard.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    public final void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }
}

