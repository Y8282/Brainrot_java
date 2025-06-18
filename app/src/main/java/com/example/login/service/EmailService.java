package com.example.login.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.login.Entity.EmailVerify;
import com.example.login.mapper.EmailMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final EmailMapper emailMapper;

    private static final String TITLE = "[DNA] Email Verification Code";
    private static final String TEXT_PREFIX = "Please copy and enter the email verification code listed below.\nVerification Code: ";

    public void sendEmail(String email, String code) {
        String text = TEXT_PREFIX + code;
        SimpleMailMessage emailForm = createEmailForm(email, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            e.printStackTrace();
            // throw new UserException(SEND_MAIL_FAILED.getMessage());
        }
    }

    private SimpleMailMessage createEmailForm(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(TITLE);
        message.setText(text);
        return message;
    }

    public void emailCodeSave(String email, String code) {
        LocalDateTime expireAt = LocalDateTime.now().plusHours(24);
        EmailVerify emailVerify = new EmailVerify(email, code, expireAt);
        emailMapper.codeUpsert(emailVerify);
    }

    public boolean isEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        Optional<EmailVerify> result = Optional.ofNullable(emailMapper.verifyEmail(params));
        return result.isEmpty();
    }

    public String getCode(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        Optional<EmailVerify> result = Optional.ofNullable(emailMapper.verifyEmail(params));
        return result.get().getCode();
    }

}