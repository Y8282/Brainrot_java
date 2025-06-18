package com.example.login.Entity;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;


@Data
public class User implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private byte[] profile_image;

    // 사용자 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // 계정 만료x
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}