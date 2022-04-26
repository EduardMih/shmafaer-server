package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.request.LoginRequestDTO;
import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.dto.response.LiveSearchUserDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    public UserDetailsDTO convertAppUserToDetailsDTO(AppUser user)
    {
        UserDetailsDTO userDetailsDTO;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<AppUser, UserDetailsDTO> typeMap = modelMapper.getTypeMap(AppUser.class, UserDetailsDTO.class);

        if(typeMap == null)
        {
            typeMap = modelMapper.createTypeMap(AppUser.class, UserDetailsDTO.class);
        }

        typeMap.addMappings(mapper -> mapper.skip(UserDetailsDTO::setRoles));
        userDetailsDTO = modelMapper.map(user, UserDetailsDTO.class);



        userDetailsDTO.setRoles(new HashSet<>());
        user.getRoles().forEach(role -> userDetailsDTO.getRoles().add(role.getName().name()));

        return userDetailsDTO;

    }

    public LiveSearchUserDTO convertAppUserToLiveSearchDTO(AppUser user)
    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(user, LiveSearchUserDTO.class);

    }


}
