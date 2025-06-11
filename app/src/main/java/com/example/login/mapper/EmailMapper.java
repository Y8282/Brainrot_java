package com.example.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.login.Entity.EmailVerify;

@Mapper
public interface EmailMapper {
    void codeUpsert(EmailVerify ev);
}
