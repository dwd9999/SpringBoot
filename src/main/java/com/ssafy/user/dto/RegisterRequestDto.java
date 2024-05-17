package com.ssafy.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(message = "아이디는 4자 이상 10자 미만이어야 합니다.",
            min = 4, max = 9)
    @Pattern(message = "아이디는 영어, 숫자만 사용 가능합니다.",
            regexp = "^[a-zA-Z0-9]+$")
    private final String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(message = "비밀번호는 4자 이상 20자 미만이어야 합니다.",
            min = 4, max = 20)
    @Pattern(message = "비밀번호는 영어, 숫자만 사용 가능합니다.",
            regexp = "^[a-zA-Z0-9]+$")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Length(message = "이름은 2자 이상 6자 미만이어야 합니다.",
            min = 2, max = 6)
    @Pattern(message = "이름은 한글과 영어만 사용 가능합니다.",
            regexp = "^[가-힣a-zA-Z]+$")
    private final String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;
}
