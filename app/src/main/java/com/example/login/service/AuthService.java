package com.example.login.service;

import org.springframework.stereotype.Service;

import com.example.login.Entity.User;
import com.example.login.mapper.LoginMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private LoginMapper loginMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User authenticate(String username, String password) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            System.out.println("AuthService @@@@@@@@@@@@@@@@@" + username);
            User user = loginMapper.selectUserByUsername(params);
            if (user == null) {
                return null; // 사용자 없음
            }
            // 비밀번호 검증 (실제로는 BCrypt 등으로 해시 비교)
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return null; // 비밀번호 불일치
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        }
    }

    public boolean registerUser(User user) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("username", user.getUsername());
            User existingUser = loginMapper.selectUserByUsername(params);
            if (existingUser != null) {
                return false;
            }
            // 해시화
            String passwordHash = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordHash);
            loginMapper.insertUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}