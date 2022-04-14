package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    Boolean existsByEmail(String email);
}
