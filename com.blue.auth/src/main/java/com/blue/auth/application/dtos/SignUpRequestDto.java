package com.blue.auth.application.dtos;

import com.blue.auth.domain.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message="유저네임은 필수 입력 항목입니다.")
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="유저네임은 알파벳 소문자와 숫자가 최소 1개 이상 포함된 4~10자리여야합니다.")
    private String userName;

    private String phoneNumber;

    @NotBlank(message="비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message="비밀번호는 알파벳 대소문자와 숫자, 특수 문자가 포함된 8~15자리여야합니다.")
    private String password;

    private UserRoleEnum role;
    private String token;
}
