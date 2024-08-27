package com.blue.auth.controller;

import com.blue.auth.application.AuthService;
import com.blue.auth.application.dtos.SignUpRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpRequestDto requestDto){
        authService.signUp(requestDto);
        return createResponse(ResponseEntity.ok(true), requestDto.getUserName());
    }

    public <T>ResponseEntity<T> createResponse(ResponseEntity<T> response, String userName){
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        headers.add("X-User-Name", userName);
        return new ResponseEntity<T>(response.getBody(), headers, response.getStatusCode());
    }
}
