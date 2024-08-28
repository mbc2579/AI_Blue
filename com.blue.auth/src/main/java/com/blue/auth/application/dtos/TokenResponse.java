package com.blue.auth.application.dtos;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class TokenResponse {
    private String accessToken;

    public static TokenResponse of(String accessToken){
        return new TokenResponse(accessToken);
    }
}
