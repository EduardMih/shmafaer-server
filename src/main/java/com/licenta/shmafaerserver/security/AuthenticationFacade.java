package com.licenta.shmafaerserver.security;

import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    @Override
    public UserDetailsImpl getAuthenticatedUser() {

        return (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

    }
}
