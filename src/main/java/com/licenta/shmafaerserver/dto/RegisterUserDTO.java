package com.licenta.shmafaerserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RegisterUserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;
    private List<String> roleName;
}
