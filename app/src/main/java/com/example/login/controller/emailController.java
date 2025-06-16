package com.example.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.Entity.Post;
import com.example.login.Entity.User;
import com.example.login.mapper.LoginMapper;
import com.example.login.model.ApiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class emailController {
    private final LoginMapper loginMapper;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> postList(){
        try {
                List<Post> posts = loginMapper.selectAllPosts();
                ApiResponse response = new ApiResponse("000", "글 불러오기");
                response.getResultData().put("posts", posts);
                return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("500", "서버 오류 : " + e.getStackTrace()));
        }
    }
    
    @Data
    static class PostRequest{
        private int id;
        private String title;
        private String content;
        private int imgId;
        private String author;
    }
}
