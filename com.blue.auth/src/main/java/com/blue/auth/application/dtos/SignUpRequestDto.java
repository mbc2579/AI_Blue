package com.blue.auth.application.dtos;

import com.blue.auth.domain.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String userName;
    private String phoneNumber;
    private String password;
    private UserRoleEnum role;
    private String token;
}
