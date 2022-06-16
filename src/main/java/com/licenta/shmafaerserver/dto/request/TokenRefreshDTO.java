package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class TokenRefreshDTO {
    @NotBlank
    private String refreshToken;
}
