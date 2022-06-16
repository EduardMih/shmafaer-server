package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.dto.request.TokenRefreshDTO;
import com.licenta.shmafaerserver.dto.response.JwtResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRefreshToken;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RefreshToken;
import com.licenta.shmafaerserver.security.jwt.JwtUtils;
import com.licenta.shmafaerserver.security.service.RefreshTokenService;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class AuthService {
    private final AuthenticationManager authMan;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public JwtResponseDTO loginUser(AppUser user)
    {
        Authentication auth = authMan.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return generateJWTResponse(auth);

    }

    public JwtResponseDTO refreshToken(TokenRefreshDTO refreshDTO) throws InvalidRefreshToken
    {
        JwtResponseDTO response = new JwtResponseDTO();
        String requestRefreshToken = refreshDTO.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        String accessJwtToken;

        if(refreshTokenService.isExpired(refreshToken))
        {
            throw new InvalidRefreshToken("Refresh token has expired");
        }

        accessJwtToken = jwtUtils.generateJWTToken(refreshToken.getUser());

        response.setJwtToken(accessJwtToken);
        response.setRefreshToken(requestRefreshToken);
        response.setEmail(refreshToken.getUser().getEmail());
        response.setFirstname(refreshToken.getUser().getFirstname());
        response.setLastname(refreshToken.getUser().getLastname());
        response.setRoles(new HashSet<>(refreshToken.getUser().getRoles()
                .stream().map((role -> role.getName().name())).collect(Collectors.toList())));

        return response;

    }

    private JwtResponseDTO generateJWTResponse(Authentication authentication)
    {
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        RefreshToken refreshToken;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(elem -> elem.getAuthority())
                .collect(Collectors.toList());

        refreshToken = refreshTokenService.createRefreshToken(userDetails.getEmail());

        jwtResponseDTO.setJwtToken(jwtUtils.generateJWTToken(authentication));
        jwtResponseDTO.setRefreshToken(refreshToken.getToken());
        jwtResponseDTO.setFirstname(userDetails.getFirstname());
        jwtResponseDTO.setLastname(userDetails.getLastname());
        jwtResponseDTO.setEmail(userDetails.getUsername());
        jwtResponseDTO.setRoles(new HashSet<>(roles));

        return jwtResponseDTO;

    }
}
