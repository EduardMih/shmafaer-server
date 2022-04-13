package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService {
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;

    public AppUser save(AppUser user)
    {

        return userRepository.save(user);

    }
}
