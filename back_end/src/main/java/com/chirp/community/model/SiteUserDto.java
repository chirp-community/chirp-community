package com.chirp.community.model;

import com.chirp.community.entity.SiteUser;
import com.chirp.community.type.RoleType;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder(toBuilder = true)
public record SiteUserDto(
        Long id,
        String email,
        String password,
        String nickname,
        RoleType role,

        String token
) implements UserDetails {
    public static SiteUserDto fromEntity(SiteUser entity, String token) {
        return SiteUserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .role(entity.getRole())

                .token(token)
                .build();
    }

    public static SiteUserDto fromEntity(SiteUser entity) {
        return fromEntity(entity, null);
    }

    public SiteUser toEntity() {
        SiteUser entity = new SiteUser();
        entity.setId(id);
        entity.setEmail(email);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRole(role);
        return entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.getDbName());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
