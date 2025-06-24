package com.example.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.post.Entity.Post;

import com.example.post.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public List<Post> getMyPosts(String email) {
        return postMapper.selectMyPosts(email);
    }
}
