package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.request.UpdateUserRolesDTO;
import com.licenta.shmafaerserver.dto.response.GetUsersResponseDTO;
import com.licenta.shmafaerserver.dto.response.MinimalUserDetailsDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
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

    public List<MinimalUserDetailsDTO> getLiveSearchResults(String namePattern, String role) throws InvalidUserRole
    {
        Role roleToSearch;
        List<MinimalUserDetailsDTO> result = new ArrayList<>();

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

    public GetUsersResponseDTO getAllUsers(Pageable pageable)
    {
        Page<AppUser> userPage = userRepository.findAll(pageable);
        List<UserDetailsDTO> userDTOs= new ArrayList<>();
        GetUsersResponseDTO result;

        userPage.getContent().forEach((user) -> userDTOs.add(userConverter.convertAppUserToDetailsDTO(user)));

        result = new GetUsersResponseDTO(userPage.getTotalElements(), userDTOs);

        return result;

    }

    public GetUsersResponseDTO getAllUsersByEmail(String email, Pageable pageable)
    {
        Page<AppUser> userPage = userRepository.findDistinctByEmailContaining(email, pageable);
        List<UserDetailsDTO> userDTOs = new ArrayList<>();
        GetUsersResponseDTO result;

        userPage.getContent().forEach((user) -> userDTOs.add(userConverter.convertAppUserToDetailsDTO(user)));

        result = new GetUsersResponseDTO(userPage.getTotalElements(), userDTOs);


        return result;

    }

    @Transactional
    public UserDetailsDTO updateUserRoles(UpdateUserRolesDTO updateUserRolesDTO) throws UnknownUserEmail, InvalidUserRole
    {
        AppUser user = userRepository.findAppUserByEmail(updateUserRolesDTO.getEmail())
                .orElseThrow(UnknownUserEmail::new);
        UserDetailsDTO response;

        user.getRoles().clear();


        try
        {
            updateUserRolesDTO.getNewRoles().forEach(role -> user.getRoles()
                    .add(roleRepository.findRoleByName(ERole.valueOf(role))));
        }
        catch(IllegalArgumentException e)
        {
            throw new InvalidUserRole();
        }

        //userRepository.save(user);

        response = userConverter.convertAppUserToDetailsDTO(user);

        return response;

    }

}
