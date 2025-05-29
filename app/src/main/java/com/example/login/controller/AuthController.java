package com.example.login.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.login.Entity.User;
import com.example.login.model.ApiResponse;
import com.example.login.service.AuthService;
import com.example.login.service.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request.getUsername(), request.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user);
            ApiResponse response = new ApiResponse("000", "로그인 성공");
            response.getResultData().put("requestId", request.getRequestId());
            response.getResultData().put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ApiResponse("500", "로그인 실패"));
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
    private String requestId;
}