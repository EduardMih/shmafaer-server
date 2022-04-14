package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.LoginRequestDTO;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.service.LoginService;
import com.licenta.shmafaerserver.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO)
    {
        String jwtToken = loginService.loginUser(userConverter.convertLoginRequestDTOToEntity(loginRequestDTO));

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);

    }
}
