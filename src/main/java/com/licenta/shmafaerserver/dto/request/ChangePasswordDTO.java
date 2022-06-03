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
public class ChangePasswordDTO {
    @NotBlank(message = "Old password can not be blank")
    private String oldPassword;

    @NotBlank(message = "Password can not be empty")
    @ValidPassword
    private String password;

    private String confirmPassword;
}
