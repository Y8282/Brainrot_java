package com.example.login.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailVerify {

    private Long id;
    private String email;
    private String code;
    private LocalDateTime expireDate;

    // 생성자
    public EmailVerify(String email, String code, LocalDateTime expireDate) {
        this.email = email;
        this.code = code;
        this.expireDate = expireDate;
    }

}
