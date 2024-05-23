package com.ssafy;

import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.user.dto.RegisterRequestDto;
import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final NoticeMapper noticeMapper;
    private final BoardMapper boardMapper;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        userMapper.registerAdmin(
                "juwon",
                encoder.encode("1234"),
                "오주원",
                "ojw1010@gmail.com");

        userMapper.register(new RegisterRequestDto(
                "gadin8631",
                "1234",
                "강다영",
                "gadin8631@naver.com"), encoder.encode("1234"));

        noticeMapper.writeNotice(new WriteRequestDto(
                        "Test Notice",
                        "Test Notice Content"),
                "test");

        boardMapper.writeBoard(new WriteRequestDto(
                        "Test Board",
                        "Test Board Content"),
                "user");


    }
}
