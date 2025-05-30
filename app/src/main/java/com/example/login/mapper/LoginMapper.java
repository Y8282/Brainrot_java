package com.example.login.mapper;

import com.example.login.Entity.User;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    User selectUserByUsername(Map<String, Object> params);
}