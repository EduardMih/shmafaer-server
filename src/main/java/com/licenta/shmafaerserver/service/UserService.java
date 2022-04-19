package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Role;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
}
