package com.example.login.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.login.Entity.User;
import com.example.login.mapper.LoginMapper;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Username cannot be empty");
        }
        User user = loginMapper.jwtByUser(username);
        if (user == null) {
            logger.error("유저를 찾을 수 없습니다: {}", username);
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다: " + username);
        }
        return user;
    }
}