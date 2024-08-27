package com.blue.auth.application.dtos;

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
    private String password;
    private String phoneNumber;
    private String ownerToken = "";
    private boolean owner = false;
    private String adminToken = "";
    private boolean admin = false;
    private String masterToken = "";
    private boolean master = false;
}
