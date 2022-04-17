package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.RegisterResponseDTO;
import com.licenta.shmafaerserver.dto.RegisterUserDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRegisterRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.service.RegisterService;
import com.licenta.shmafaerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterUserDTO newUserDTO)
            throws UserAlreadyExists, InvalidRegisterRole, InvalidStudentID
    {
        AppUser user = userConverter.convertRegisterUserDTOToEntity(newUserDTO);
        RegisterResponseDTO responseDTO = new RegisterResponseDTO();

        user = registerService.registerNewUser(user);

        responseDTO.setFirstname(user.getFirstname());
        responseDTO.setLastname(user.getLastname());
        responseDTO.setMessage("Success");

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }
}
