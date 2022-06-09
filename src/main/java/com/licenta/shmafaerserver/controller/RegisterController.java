package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.response.RegisterResponseDTO;
import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRegisterRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterUserDTO newUserDTO)
            throws UserAlreadyExists, InvalidRegisterRole, InvalidStudentID
    {
        RegisterResponseDTO response = registerService.registerNewUser(newUserDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
