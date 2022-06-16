package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    boolean existsByUserEmail(String email);
    void deleteByUserEmail(String email);
}
