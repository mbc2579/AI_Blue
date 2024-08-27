package com.blue.auth.application.dtos;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class AuthResponseDto {
    private String accessToken;

    public static AuthResponseDto of(String accessToken){
        return new AuthResponseDto(accessToken);
    }
}
