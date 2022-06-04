package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.ChangePasswordDTO;
import com.licenta.shmafaerserver.dto.request.UpdateUserInfoDTO;
import com.licenta.shmafaerserver.dto.response.JwtResponseDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidOldPassword;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/myProfile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @PatchMapping("/update")
    public ResponseEntity<Object> updateUserInfo(@Valid @RequestBody UpdateUserInfoDTO updatedUserInfo)
            throws UserAlreadyExists
    {
        UserDetailsDTO response = profileService.updateUserInfo(updatedUserInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO)
            throws InvalidOldPassword
    {
        UserDetailsDTO response = profileService.changePassword(changePasswordDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
