package com.licenta.shmafaerserver.security.service;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AppUser user = userRepository.findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        return UserDetailsImpl.builder(user);

    }
}
