package com.licenta.shmafaerserver.security;

import com.licenta.shmafaerserver.security.service.UserDetailsImpl;

public interface IAuthenticationFacade {

    UserDetailsImpl getAuthenticatedUser();

}
