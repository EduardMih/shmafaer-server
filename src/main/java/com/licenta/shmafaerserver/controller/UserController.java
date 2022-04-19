package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUsersByRole(@RequestParam(name = "role") String role)
            throws InvalidUserRole
    {
        List<UserDetailsDTO> users = userService.getUsersByRoles(List.of(role));

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
