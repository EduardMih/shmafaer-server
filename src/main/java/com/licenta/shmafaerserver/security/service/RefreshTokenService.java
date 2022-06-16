package com.licenta.shmafaerserver.security.service;

import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidRefreshToken;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RefreshToken;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    private static final Long REFRESH_TOKEN_DURATION = 20L;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository userRepository;

    public RefreshToken createRefreshToken(String email)
    {
        RefreshToken refreshToken = new RefreshToken();
        AppUser user= userRepository.findAppUserByEmail(email).get();

        deleteToken(email);

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusHours(REFRESH_TOKEN_DURATION));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;

    }

    public boolean isExpired(RefreshToken refreshToken)
    {

        return refreshToken.getExpiryDate().isBefore(LocalDateTime.now());

    }

    public RefreshToken findByToken(String token) throws InvalidRefreshToken
    {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshToken("Refresh token is invalid"));
    }

    public void deleteToken(String email)
    {
        if(refreshTokenRepository.existsByUserEmail(email))
        {
            refreshTokenRepository.deleteByUserEmail(email);
        }

    }

}
