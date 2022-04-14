package com.licenta.shmafaerserver.security.service;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl builder(AppUser user)
    {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );

    }

    @Override
    public String getUsername()
    {

        return email;

    }

    @Override
    public boolean isAccountNonExpired()
    {

        return true;

    }

    @Override
    public boolean isAccountNonLocked()

    {
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired()
    {

        return true;

    }

    @Override
    public boolean isEnabled()
    {

        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
