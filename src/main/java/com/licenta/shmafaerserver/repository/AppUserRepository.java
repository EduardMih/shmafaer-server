package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByEmail(String email);
    Boolean existsByEmail(String email);
}
