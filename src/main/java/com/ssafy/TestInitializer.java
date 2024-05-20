package com.ssafy;

import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        userMapper.registerAdmin(
                "test",
                encoder.encode("test"),
                "test",
                "test@test.com");
    }
}
