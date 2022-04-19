package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.response.JwtResponseDTO;
import com.licenta.shmafaerserver.dto.request.LoginRequestDTO;
import com.licenta.shmafaerserver.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO)
    {
        JwtResponseDTO jwtResponseDTO = loginService.loginUser(userConverter.convertLoginRequestDTOToEntity(loginRequestDTO));

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);

    }
}
