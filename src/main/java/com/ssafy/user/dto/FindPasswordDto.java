package com.ssafy.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@RequiredArgsConstructor
public class FindPasswordDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(message = "아이디는 4자 이상 10자 미만이어야 합니다.",
            min = 4, max = 9)
    @Pattern(message = "아이디는 영어, 숫자만 사용 가능합니다.",
            regexp = "^[a-zA-Z0-9]+$")
    private final String userId;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;
}
