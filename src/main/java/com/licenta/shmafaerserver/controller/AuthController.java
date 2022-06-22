package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.request.TokenRefreshDTO;
import com.licenta.shmafaerserver.dto.response.JwtResponseDTO;
import com.licenta.shmafaerserver.dto.request.LoginRequestDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRefreshToken;
import com.licenta.shmafaerserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
        //, allowCredentials = "true")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserConverter userConverter;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                                        HttpServletResponse response)
    {
        JwtResponseDTO jwtResponseDTO = authService.loginUser(userConverter.convertLoginRequestDTOToEntity(loginRequestDTO));

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(@Valid @RequestBody TokenRefreshDTO refreshDTO)
            throws InvalidRefreshToken
    {
        JwtResponseDTO jwtResponseDTO = authService.refreshToken(refreshDTO);

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);

    }
}
