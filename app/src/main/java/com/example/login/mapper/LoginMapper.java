package com.example.login.mapper;

import com.example.login.Entity.Post;
import com.example.login.Entity.User;

import java.util.*;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    User selectUserByUsername(Map<String, Object> params);

    void insertUser(User user);

    int updateUserImage(String email ,byte[] profile_image);

    User selectUserByEmail(String email);

    List<Post> selectPostsByail(String email);

    List<Post> selectAllPosts();
}