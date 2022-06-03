package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class UpdateUserInfoDTO {
    private String firstname;
    private String lastname;

    @Email(message = "Email must have a valid format")
    private String email;
}
