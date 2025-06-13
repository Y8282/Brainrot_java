package com.example.login.controller;

import com.example.login.Entity.User;
import com.example.login.model.ApiResponse;
import com.example.login.service.AuthService;
import com.example.login.service.JwtUtil;

import jakarta.validation.Valid;
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
            System.out.println("@@@@@@@@@@@@@@@@@" + request.getUsername());
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

    @PostMapping("/change/pass")
    public ResponseEntity<?> findPass(@Valid @RequestBody ChangePassRequest reqeustParam) {
        try {
            boolean auth = authService.verifyCode(reqeustParam.getEmail(), reqeustParam.getCode());
            if (auth) {
                boolean success = authService.changePass(reqeustParam.getEmail(), reqeustParam.getPassword());
                if (success) {
                    return ResponseEntity.ok(new ApiResponse("000", "비밀번호가 성공적으로 변경됐습니다."));
                }
                return ResponseEntity.status(400).body(new ApiResponse("400", "기존에 사용했던 비밀번호 입니다."));
            } else
                return ResponseEntity.status(500).body(new ApiResponse("500", "비상 비상 일어날 수 없는 가능성!!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
    }

    @PostMapping("/emails/verify")
    public ResponseEntity<ApiResponse> sendCode(@Valid @RequestBody SendCodeRequest reqeustParam) {
        try {
            ApiResponse response = authService.sendCode(reqeustParam.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
    }

    @GetMapping("/emails/verify")
    public ResponseEntity<ApiResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest reqeustParam) {
        try {
            boolean response = authService.verifyCode(reqeustParam.getEmail(), reqeustParam.getCode());
            if (response)
                return ResponseEntity.ok(new ApiResponse("000", "인증번호가 일치하여 확인이 되었습니다."));
            else
                return ResponseEntity.status(400).body(new ApiResponse("400", "인증번호가 일치하지 않습니다."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));
        }
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
    }

    @Data
    static class ChangePassRequest {
        private String email;
        private String code;
        private String password;
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