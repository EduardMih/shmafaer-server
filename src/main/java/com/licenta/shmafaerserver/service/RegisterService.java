package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.dto.response.RegisterResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRegisterRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.service.accountconfirmation.AccountConfirmationService;
import com.licenta.shmafaerserver.service.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class RegisterService {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountConfirmationService accountConfirmationService;
    private final UserConverter userConverter;
    private final UserUtils userUtils;

    public RegisterResponseDTO registerNewUser(RegisterUserDTO newUserDTO) throws UserAlreadyExists, InvalidRegisterRole, InvalidStudentID
    {
        AppUser user;
        RegisterResponseDTO responseDTO = new RegisterResponseDTO();

        userUtils.validateRegisterDTO(newUserDTO);

        user = userConverter.convertRegisterUserDTOToEntity(newUserDTO);
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user = userRepository.save(user);

        accountConfirmationService.sendConfirmToken(user);

        responseDTO.setFirstname(user.getFirstname());
        responseDTO.setLastname(user.getLastname());
        responseDTO.setMessage("Success");

        return responseDTO;

    }
}
