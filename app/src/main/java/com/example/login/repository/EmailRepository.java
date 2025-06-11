package com.example.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.login.Entity.EmailVerify;

public interface EmailRepository extends JpaRepository<EmailVerify, Long> {

    Optional<EmailVerify> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO email_verify (id, email, code, expire_date) " +
            "VALUES (:#{#ev.id}, :#{#ev.email}, :#{#ev.code}, :#{#ev.expireDate}) " +
            "ON DUPLICATE KEY UPDATE " +
            "code      = VALUES(code), " +
            "expire_date = VALUES(expire_date)", nativeQuery = true)
    int upsert(@Param("ev") EmailVerify ev);
}
