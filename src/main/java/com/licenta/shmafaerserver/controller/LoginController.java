package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.response.JwtResponseDTO;
import com.licenta.shmafaerserver.dto.request.LoginRequestDTO;
import com.licenta.shmafaerserver.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
        //, allowCredentials = "true")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                                        HttpServletResponse response)
    {
        JwtResponseDTO jwtResponseDTO = loginService.loginUser(userConverter.convertLoginRequestDTOToEntity(loginRequestDTO));
        //Cookie cookie = new Cookie("jwtToken", jwtResponseDTO.getJwtToken());

        //cookie.setHttpOnly(true);
        //cookie.setMaxAge(7 * 24 * 60 * 60);
        //cookie.setPath("/");

        //response.addCookie(cookie);

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);

    }
}
