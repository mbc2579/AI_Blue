package com.blue.auth.controller;

import com.blue.auth.application.AuthService;
import com.blue.auth.application.dtos.SignUpRequestDto;
import com.blue.auth.security.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logIn")
    public ResponseEntity<Boolean> logIn(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userName = userDetails.getUsername();
        return createResponse(ResponseEntity.ok(true), userName);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpRequestDto requestDto){
        authService.signUp(requestDto);
        String userName = requestDto.getUserName();
        return createResponse(ResponseEntity.ok(true), userName);
    }

    @PostMapping("/refresh")

    @GetMapping("/authority")

    @PutMapping("/{userName}/edit")

    @DeleteMapping("/{userName}/withdraw")

    public <T>ResponseEntity<T> createResponse(ResponseEntity<T> response, String userName){
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        headers.add("X-User-Name", userName);
        return new ResponseEntity<T>(response.getBody(), headers, response.getStatusCode());
    }
}
