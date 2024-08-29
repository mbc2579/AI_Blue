package com.blue.auth.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequestDto {
    private String userName;
    private String password;
}
