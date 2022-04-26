package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.response.LiveSearchUserDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/liveSearch")
    public ResponseEntity<Object> liveSearchUser(@RequestParam(name = "namePattern") String namePattern,
                                                 @RequestParam(name = "role", required = false) String role)
            throws InvalidUserRole
    {
        List<LiveSearchUserDTO> users = userService.getLiveSearchResults(namePattern, role);
        log.info("cautat " + namePattern);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<Object> getUsers(@RequestParam(name = "email", required = false) String email,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "3") int size)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastname").ascending().and(Sort.by("firstname").ascending()));
        List<UserDetailsDTO> users;

        if(email != null)
        {
            users = userService.getAllUsersByEmail(email, pageable);
        }

        else
        {
            users = userService.getAllUsers(pageable);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);

    }
}
