package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.PasswordResetDTO;
import com.licenta.shmafaerserver.dto.response.PasswordResetResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidPasswordResetToken;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.service.resetpassword.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/resetPassword")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @GetMapping
    public ResponseEntity<Object> requestPasswordReset(@RequestParam(name = "email") String email)
            throws UnknownUserEmail
    {
        PasswordResetResponseDTO response = passwordResetService.initializeResetPassword(email);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO)
            throws InvalidPasswordResetToken, UnknownUserEmail
    {
        PasswordResetResponseDTO response = passwordResetService.resetPassword(passwordResetDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
