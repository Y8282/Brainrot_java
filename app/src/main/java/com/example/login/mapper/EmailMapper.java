package com.example.login.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.login.Entity.EmailVerify;

@Mapper
public interface EmailMapper {
    void codeUpsert(EmailVerify ev);
    EmailVerify verifyEmail(Map<String,Object> params); 
}
