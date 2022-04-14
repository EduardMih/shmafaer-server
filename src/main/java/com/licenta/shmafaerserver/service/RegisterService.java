package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class RegisterService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AppUser registerNewUser(AppUser newUser)
    {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userService.save(newUser);

    }

}
