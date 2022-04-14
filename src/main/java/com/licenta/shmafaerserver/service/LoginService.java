package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.security.jwt.JwtUtils;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class LoginService {
    private final AuthenticationManager authMan;
    private final JwtUtils jwtUtils;

    public String loginUser(AppUser user)
    {
        Authentication auth = authMan.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return generateJWTToken(auth);

    }

    private String generateJWTToken(Authentication authentication)
    {
        //add more details like roles to token
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(elem -> elem.getAuthority())
                .collect(Collectors.toList());

        return jwtUtils.generateJWTToken(authentication);

    }
}