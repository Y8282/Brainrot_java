// package com.example.login.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.login.Entity.User;
// import com.example.login.mapper.LoginMapper;
// import com.example.login.model.ApiResponse;
// import com.example.login.service.AuthService;

// import lombok.Data;
// import lombok.RequiredArgsConstructor;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @RestController
// @RequestMapping("/api/user")
// @RequiredArgsConstructor
// public class userController {
//     private final LoginMapper loginMapper;

//     @PostMapping("/{email}/image")
//     public ResponseEntity<ApiResponse> Insertimage(@PathVariable String email,
//             @RequestBody ImageRequest request) {
//         try {
//             User user = loginMapper.selectUserByEmail(email);
//             if (user == null) {
//                 return ResponseEntity.badRequest()
//                         .body(new ApiResponse("400", "없는 유저 입니다."));
//             }
//             int rows = loginMapper.updateUserImage(email, request.getProfile_image());
//             if (rows == 1) {
//                 ApiResponse response = new ApiResponse("000", "이미지 저장");
//                 return ResponseEntity.ok(response);
//             } else {
//                 return ResponseEntity.badRequest()
//                         .body(new ApiResponse("400", "이미지 저장 실패"));
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             return ResponseEntity.status(500)
//                     .body(new ApiResponse("500", "서버 오류: " + e.getMessage()));

//         }
//     }

//     @Data
//     static class ImageRequest {
//         private String username;
//         private String email;
//         private byte[] profile_image;
//         private String requestId;
//     }
// }
