package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class UserDetailsDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String institutionalID;
    private Set<String> roles;

}
