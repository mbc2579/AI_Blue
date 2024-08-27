package com.blue.auth.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LogInRequestDto {
    private String userName;
    private String password;
}
