// package com.example.login.config;

// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Profile;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.example.login.Entity.User;
// import com.example.login.repository.UserRepository;

// @Profile("dev") // 개발환경에서만 테스트트
// @Component
// @RequiredArgsConstructor
// public class DataInitializer implements CommandLineRunner {
// private final UserRepository userRepository;
// private final PasswordEncoder passwordEncoder;

// @Override
// public void run(String... args) throws Exception {
// if(userRepository.findByUsername("testuser").isEmpty()){
// User user = new User();
// user.setUsername("testuser");
// user.setPassword(passwordEncoder.encode("password"));
// userRepository.save(user);
// }
// }
// }