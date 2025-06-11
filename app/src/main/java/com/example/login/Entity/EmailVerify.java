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

@Entity
@Table(name = "emailVerify")
@Data
@NoArgsConstructor
public class EmailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, name = "expire_date")
    private LocalDateTime expireDate;

    // 생성자
    public EmailVerify(String email, String code, LocalDateTime expireDate) {
        this.email = email;
        this.code = code;
        this.expireDate = expireDate;
    }

}
