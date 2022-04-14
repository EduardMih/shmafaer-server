package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.ERole;
import com.licenta.shmafaerserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(ERole name);
}
