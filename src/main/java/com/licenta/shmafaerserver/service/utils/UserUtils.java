package com.licenta.shmafaerserver.service.utils;

import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRegisterRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final AppUserRepository userRepository;

    public void validateRegisterDTO(RegisterUserDTO newUserDTO) throws UserAlreadyExists, InvalidRegisterRole, InvalidStudentID
    {
        if(checkEmailIsUnique(newUserDTO))
        {
            throw new UserAlreadyExists();
        }

        if(!checkRoles(newUserDTO))
        {
            throw new InvalidRegisterRole();
        }

        if(!checkStudentID(newUserDTO))
        {

            throw new InvalidStudentID();

        }
    }

    public void validateAdminCreateUser(RegisterUserDTO newUserDTO) throws UserAlreadyExists, InvalidStudentID
    {
        if(checkEmailIsUnique(newUserDTO))
        {
            throw new UserAlreadyExists();
        }

        if(!checkStudentID(newUserDTO))
        {

            throw new InvalidStudentID();

        }
    }

    public boolean checkEmailIsUnique(RegisterUserDTO newUserDTO)
    {

        return userRepository.existsByEmail(newUserDTO.getEmail());

    }

    public boolean checkRoles(RegisterUserDTO newUserDTO)
    {

        return (Objects.equals(newUserDTO.getRoleName(), ERole.USER.name())) || (Objects.equals(newUserDTO.getRoleName(), ERole.STUDENT.name()));

    }

    public boolean checkStudentID(RegisterUserDTO newUserDTO)
    {
        if(Objects.equals(newUserDTO.getRoleName(), ERole.STUDENT.name()))
        {

            return (newUserDTO.getInstitutionalID() != null) && (newUserDTO.getInstitutionalID().startsWith("310"));

        }

        return true;

    }
}
