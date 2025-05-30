package com.example.login.controller;

import com.example.login.Entity.User;
import com.example.login.model.ApiResponse;
import com.example.login.service.AuthService;
import com.example.login.service.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(new ApiResponse("401", "사용자 인증 실패"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
    private String requestId;
}