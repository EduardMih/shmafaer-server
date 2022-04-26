package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.response.LiveSearchUserDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Role;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService {
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;

    public AppUser save(AppUser user)
    {

        return userRepository.save(user);

    }

    public List<UserDetailsDTO> getUsersByRoles(List<String> roles) throws InvalidUserRole
    {
        List<Role> rolesToSearch = new ArrayList<>();
        List<UserDetailsDTO> result = new ArrayList<>();
        try
        {
            for(String role: roles)
            {
                rolesToSearch.add(roleRepository.findRoleByName(ERole.valueOf(role)));
            }
        }
        catch(IllegalArgumentException e)
        {
            throw new InvalidUserRole();
        }

        userRepository.findDistinctByRolesIn(rolesToSearch).forEach(user -> {
            result.add(userConverter.convertAppUserToDetailsDTO(user));
        });


        return result;

    }

    public List<LiveSearchUserDTO> getLiveSearchResults(String namePattern, String role) throws InvalidUserRole
    {
        Role roleToSearch;
        List<LiveSearchUserDTO> result = new ArrayList<>();

        if(role == null)
        {
            userRepository.findDistinctByFirstnameContainingOrLastnameContaining(namePattern.toLowerCase(), namePattern.toLowerCase()).forEach(user -> {
                result.add(userConverter.convertAppUserToLiveSearchDTO(user));
            });
        }

        else

        {
            try
            {
                roleToSearch = roleRepository.findRoleByName(ERole.valueOf(role));
            }
            catch (IllegalArgumentException e)
            {
                throw new InvalidUserRole();
            }

            userRepository.findByNamePatternAndRole(namePattern.toLowerCase(), namePattern.toLowerCase(), Arrays.asList(roleToSearch)).forEach(user -> {
                result.add(userConverter.convertAppUserToLiveSearchDTO(user));
            });
        }

        return result;

    }

    public List<UserDetailsDTO> getAllUsers(Pageable pageable)
    {
        List<AppUser> users = userRepository.findAll(pageable).getContent();
        List<UserDetailsDTO> result = new ArrayList<>();

        users.forEach((user) -> result.add(userConverter.convertAppUserToDetailsDTO(user)));

        return result;

    }

    public List<UserDetailsDTO> getAllUsersByEmail(String email, Pageable pageable)
    {
        List<AppUser> users = userRepository.findDistinctByEmailContaining(email, pageable).getContent();
        List<UserDetailsDTO> result = new ArrayList<>();

        users.forEach((user) -> result.add(userConverter.convertAppUserToDetailsDTO(user)));

        return result;

    }

}
