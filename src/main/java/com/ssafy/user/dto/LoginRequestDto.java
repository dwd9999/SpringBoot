package com.ssafy.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private final String id;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;
}
