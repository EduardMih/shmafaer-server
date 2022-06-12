package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.RegisterUserDTO;
import com.licenta.shmafaerserver.dto.request.UpdateUserRolesDTO;
import com.licenta.shmafaerserver.dto.response.GetUsersResponseDTO;
import com.licenta.shmafaerserver.dto.response.MinimalUserDetailsDTO;
import com.licenta.shmafaerserver.dto.response.RegisterResponseDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidStudentID;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidUserRole;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import com.licenta.shmafaerserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterUserDTO newUser)
            throws InvalidStudentID, UserAlreadyExists
    {
        //RegisterResponseDTO response = userService.saveNewUser(newUser);

        //return new ResponseEntity<>(response, HttpStatus.OK);
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/liveSearch")
    public ResponseEntity<Object> liveSearchUser(@RequestParam(name = "namePattern") String namePattern,
                                                 @RequestParam(name = "role", required = false) String role)
            throws InvalidUserRole
    {
        List<MinimalUserDetailsDTO> users = userService.getLiveSearchResults(namePattern, role);
        log.info("cautat " + namePattern);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<Object> getUsers(@RequestParam(name = "email", required = false) String email,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "3") int size)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastname").ascending().and(Sort.by("firstname").ascending()));
        GetUsersResponseDTO response;

        if(email != null)
        {
            response = userService.getAllUsersByEmail(email, pageable);
        }

        else
        {
            response = userService.getAllUsers(pageable);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("update/roles")
    public ResponseEntity<Object> updateRoles(@RequestBody UpdateUserRolesDTO updateUserRolesDTO)
            throws UnknownUserEmail, InvalidUserRole
    {
        UserDetailsDTO updatedUser = userService.updateUserRoles(updateUserRolesDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}
