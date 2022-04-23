package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    Boolean existsByEmail(String email);
    List<AppUser> findDistinctByRolesIn(Collection<Role> roles);

    @Query("SELECT DISTINCT u FROM AppUser u WHERE lower(u.firstname) LIKE %:firstnamePattern% OR lower(u.lastname) LIKE %:lastnamePattern%")
    List<AppUser> findDistinctByFirstnameContainingOrLastnameContaining(@Param("firstnamePattern") String firstnamePattern,
                                                                        @Param("lastnamePattern") String lastnamePattern);

    @Query("SELECT DISTINCT u FROM AppUser u JOIN FETCH u.roles r WHERE (lower(u.firstname) LIKE %:firstnamePattern% OR lower(u.lastname) LIKE %:lastnamePattern%) AND (r IN (:roles))")
    List<AppUser> findByNamePatternAndRole(@Param("firstnamePattern") String firstnamePattern,
                                           @Param("lastnamePattern") String lastnamePattern,
                                           @Param("roles") Collection<Role> roles);
}
