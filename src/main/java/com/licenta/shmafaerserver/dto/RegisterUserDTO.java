package com.licenta.shmafaerserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RegisterUserDTO {

    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password can not be empty")
    private String password;

    private String confirmPassword;
    private List<String> roleName;
}
