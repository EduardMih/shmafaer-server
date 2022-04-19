package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.request.LoginRequestDTO;
import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


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

        if(Objects.equals(user.getInstitutionalID().trim(),""))
        {
            user.setInstitutionalID(null);
        }

        user.getRoles().add(roleRepository.findRoleByName(ERole.valueOf(registerUserDTO.getRoleName())));

        return user;

    }

    public AppUser convertLoginRequestDTOToEntity(LoginRequestDTO loginRequestDTO)
    {

        return modelMapper.map(loginRequestDTO, AppUser.class);

    }
}
