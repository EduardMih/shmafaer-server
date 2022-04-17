package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.LoginRequestDTO;
import com.licenta.shmafaerserver.dto.RegisterUserDTO;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.ERole;
import com.licenta.shmafaerserver.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    public AppUser convertRegisterUserDTOToEntity(RegisterUserDTO registerUserDTO)
    {
        AppUser user;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        user = modelMapper.map(registerUserDTO, AppUser.class);

        user.getRoles().add(roleRepository.findRoleByName(ERole.valueOf(registerUserDTO.getRoleName())));

        return user;

    }

    public AppUser convertLoginRequestDTOToEntity(LoginRequestDTO loginRequestDTO)
    {

        return modelMapper.map(loginRequestDTO, AppUser.class);

    }
}
