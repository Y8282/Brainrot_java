package com.example.login.service;

import org.springframework.stereotype.Service;

import com.example.login.Entity.User;
import com.example.login.mapper.LoginMapper;
import com.example.login.model.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User authenticate(String username, String password) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);

            User user = loginMapper.selectUserByUsername(params);

            if (user == null) {
                return null; // 사용자 없음
            }
            // 비밀번호 검증 (실제로는 BCrypt 등으로 해시 비교)
            System.out.println("Password @@@@@@@@@@@@@@@@@@@@@" + password);
            System.out.println("getPass @@@@@@@@@@@@@@@@@@@@@" + user.getPassword());
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

    public boolean changePass(String email, String password) {
        try {
            Map<String, Object> authParams = new HashMap<>();
            authParams.put("email", email);
            User oldUser = loginMapper.selectUserByEmail(authParams);
            // 기존 비번과 중복 여부 확인
            if (passwordEncoder.matches(password, oldUser.getPassword())) {
                return false;
            }
            String passwordHash = passwordEncoder.encode(password);
            Map<String, Object> changeParams = new HashMap<>();
            changeParams.put("email", email);
            changeParams.put("password", passwordHash);
            loginMapper.changePassword(changeParams);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ApiResponse sendCode(String email) {
        try {
            Map<String, Object> authParams = new HashMap<>();
            authParams.put("email", email);
            User user = loginMapper.selectUserByEmail(authParams);
            // 이메일 인증 때에 중복검사
            if (user != null) {
                String code = createCode();
                emailService.sendEmail(email, code);
                emailService.emailCodeSave(email, code);
                return new ApiResponse("000", "성공적으로 code 보냄 및 저장됨");
            } else {
                return new ApiResponse("400", "이미 가입된 이메일 입니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("400", "이메일 보내던 중 오류 또는 저장 중 오류");
        }
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("인증 코드 생성 실패", e);
        }
    }

    public boolean verifyCode(String email, String code) {
        String findCode = emailService.getCode(email);
        boolean isVerified = code.equals(findCode);
        return isVerified;
    }
}