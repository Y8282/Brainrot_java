package com.example.login.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.login.Entity.User;

@Mapper
public interface LoginMapper {
    User selectUserByUsername(Map<String, Object> params);

    void insertUser(User user);

    User selectUserByEmail(Map<String, Object> params);

    void changePassword(Map<String, Object> params);

    User jwtByUser(String username);

}