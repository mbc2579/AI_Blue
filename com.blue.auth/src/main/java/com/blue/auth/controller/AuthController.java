package com.blue.auth.controller;

import com.blue.auth.application.AuthService;
import com.blue.auth.application.dtos.LogInRequestDto;
import com.blue.auth.application.dtos.SignUpRequestDto;
import com.blue.auth.application.dtos.UpdateRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logIn")
    public ResponseEntity<Boolean> logIn(@RequestBody LogInRequestDto requestDto, HttpServletResponse response){
        authService.logIn(requestDto, response);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody SignUpRequestDto requestDto){
        authService.signUp(requestDto);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{userName}/edit")
    public String userEdit(@PathVariable String userName, @Valid @RequestBody UpdateRequestDto requestDto ){
        authService.userEdit(userName, requestDto);
        return "true";
    }

    @DeleteMapping("/{userName}/withdraw")
    public ResponseEntity<Boolean> userWithdraw(@PathVariable String userName){
        authService.userWithdraw(userName);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/authority")
    public String getAuthority(@RequestParam(name="userName") String userName){
        String role= authService.getAuthority(userName);
        return role;
    }
}
