package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.ConfirmAccountDTO;
import com.licenta.shmafaerserver.dto.response.ConfirmAccountResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidConfirmationToken;
import com.licenta.shmafaerserver.service.accountconfirmation.AccountConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/confirmAccount")
@RequiredArgsConstructor
public class AccountConfirmationController {
    private final AccountConfirmationService accountConfirmationService;

    @PatchMapping
    public ResponseEntity<Object> confirmEmail(@Valid @RequestBody ConfirmAccountDTO confirmAccountDTO)
            throws InvalidConfirmationToken
    {
        ConfirmAccountResponseDTO response = accountConfirmationService.confirmAccount(confirmAccountDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/resendToken")
    public ResponseEntity<Object> resendConfirmationToken(@Valid @RequestBody ConfirmAccountDTO confirmAccountDTO)
            throws InvalidConfirmationToken
    {
        ConfirmAccountResponseDTO response = accountConfirmationService.resendConfirmToken(confirmAccountDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
