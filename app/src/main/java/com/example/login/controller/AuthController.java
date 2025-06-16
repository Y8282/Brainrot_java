package com.example.login.controller;

import com.example.login.Entity.User;
import com.example.login.model.ApiResponse;
import com.example.login.service.AuthService;
import com.example.login.service.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            if (request.getUsername() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("400", "username 또는 password가 누락되었습니다."));
            }
            User user = authService.authenticate(request.getUsername(), request.getPassword());
            if (user != null) {
                String token = jwtUtil.generateToken(user);
                ApiResponse response = new ApiResponse("000", "로그인 성공");
                response.getResultData().put("requestId", request.getRequestId());
                response.getResultData().put("token", token);
                response.getResultData().put("username", request.getUsername());
                response.getResultData().put("email", user.getEmail());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(new ApiResponse("401", "사용자 인증 실패"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) {
        try {
            if (request.getUsername() == null || request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("400", "필수 입력값이 누락되었습니다."));
            }
            // 이메일 형식 검증
            if (!request.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("400", "유효한 이메일 형식이 아닙니다."));
            }
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            boolean success = authService.registerUser(user);
            if (success) {
                return ResponseEntity.ok(new ApiResponse("000", "회원가입 성공"));
            }
            return ResponseEntity.status(409)
                    .body(new ApiResponse("409", "이미 존재하는 사용자입니다."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
    }

    @PostMapping("/emails/verify")
    public ResponseEntity<ApiResponse> sendCode(@Valid @RequestBody SendCodeRequest reqeustParam) {
        ApiResponse response = authService.sendCode(reqeustParam.getEmail());
        return ResponseEntity.status(500)
                .body(response);
    }

    @GetMapping("/emails/verify")
    public ResponseEntity<ApiResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest reqeustParam) {
        ApiResponse response = authService.verifyCode(reqeustParam.getEmail(), reqeustParam.getCode());
        return ResponseEntity.ok(response);
    }

    @Data
    static class SignupRequest {
        private String username;
        private String email;
        private String password;
        private String requestId;
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
        private String requestId;
        private String email;
        private byte[] profile_image;
    }

    @Data
    static class SendCodeRequest {
        private String email;
    }

    @Data
    static class VerifyCodeRequest {
        private String email;
        private String code;
    }

}