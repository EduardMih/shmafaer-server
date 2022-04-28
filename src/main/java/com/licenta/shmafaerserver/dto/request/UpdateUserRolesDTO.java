package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class UpdateUserRolesDTO {
    private String email;
    private Set<String> newRoles;
}
