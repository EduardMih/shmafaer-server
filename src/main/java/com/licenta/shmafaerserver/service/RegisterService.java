package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRegisterRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.ERole;
import com.licenta.shmafaerserver.model.Role;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class RegisterService {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AppUser registerNewUser(AppUser newUser) throws UserAlreadyExists, InvalidRegisterRole, InvalidStudentID
    {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if(userRepository.existsByEmail(newUser.getEmail()))
        {
            throw new UserAlreadyExists();
        }

        if(!checkRoles(newUser))
        {
            throw new InvalidRegisterRole();
        }

        if(!checkStudentID(newUser))
        {

            throw new InvalidStudentID();

        }

        return userRepository.save(newUser);

    }

    private boolean checkRoles(AppUser newUser)
    {
        for(Role role: newUser.getRoles())
        {
            if ((role.getName() != ERole.USER) && (role.getName() != ERole.STUDENT))
            {

                return false;

            }
        }

        return true;

    }

    private boolean checkStudentID(AppUser newUser)
    {
        if(newUser.getRoles().get(0).getName() == ERole.STUDENT)
        {

            return (newUser.getInstitutionalID() != null) && (newUser.getInstitutionalID().startsWith("310"));

        }

        return true;

    }
}
