package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class JwtResponseDTO {
    private String jwtToken;
    private String firstname;
    private String lastname;
    private Set<String> roles;
}
