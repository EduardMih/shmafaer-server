package com.licenta.shmafaerserver.dto.request;

import com.licenta.shmafaerserver.validator.PasswordsMatch;
import com.licenta.shmafaerserver.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@PasswordsMatch(
        password = "password",
        confirmPassword = "confirmPassword"
)
public class PasswordResetDTO {
    @NotBlank(message = "Password can not be empty")
    @ValidPassword
    private String password;
    private String confirmPassword;

    private String token;

}
