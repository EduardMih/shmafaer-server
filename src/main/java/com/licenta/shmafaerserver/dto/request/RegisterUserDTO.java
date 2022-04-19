package com.licenta.shmafaerserver.dto.request;


import com.licenta.shmafaerserver.validator.PasswordsMatch;
import com.licenta.shmafaerserver.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
@PasswordsMatch(
                password = "password",
                confirmPassword = "confirmPassword"
)
public class RegisterUserDTO {

    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must have a valid format")
    private String email;

    private String institutionalID;

    @NotBlank(message = "Password can not be empty")
    @ValidPassword
    private String password;
    private String confirmPassword;

    private String roleName;
}
