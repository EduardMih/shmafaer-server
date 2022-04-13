package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.RegisterUserDTO;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Role;
import com.licenta.shmafaerserver.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    public AppUser convertRegisterUserDTOToEntity(RegisterUserDTO registerUserDTO)
    {
        AppUser user = modelMapper.map(registerUserDTO, AppUser.class);
        List<Role> userRoles = new ArrayList<>();

        registerUserDTO.getRoleName().
                forEach(roleName -> user.getRoles().add(roleRepository.findRoleByName(roleName)));

        return user;

    }
}
