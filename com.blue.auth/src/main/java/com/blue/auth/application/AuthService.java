package com.blue.auth.application;

import com.blue.auth.application.dtos.LogInRequestDto;
import com.blue.auth.application.dtos.SignUpRequestDto;
import com.blue.auth.application.dtos.TokenResponse;
import com.blue.auth.domain.User;
import com.blue.auth.domain.UserRepository;
import com.blue.auth.domain.UserRoleEnum;
import com.blue.auth.jwt.JwtUtil;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 임시로 정해둔 Key 값
    private final String ADMIN_TOKEN = "ADMIN";
    private final String MASTER_TOKEN = "MASTER";

    @Transactional
    public void signUp(final SignUpRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String phoneNumber = requestDto.getPhoneNumber();
        UserRoleEnum role = requestDto.getRole();

        Optional<User> checkUserName = userRepository.findByUserName(userName);
        if(checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 존재");
        }

        if(!requestDto.getToken().isEmpty()){
            if(requestDto.getToken().equals(ADMIN_TOKEN)&&role == UserRoleEnum.ADMIN) {
                role = UserRoleEnum.ADMIN;
            }else if(requestDto.getToken().equals(MASTER_TOKEN)&&role == UserRoleEnum.MASTER) {
                role = UserRoleEnum.MASTER;
            }
        }

        User user = new User(userName, phoneNumber, password, role);
        userRepository.save(user);
    }

    public void logIn(LogInRequestDto requestDto, HttpServletResponse response){
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getUserName(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);
    }

}
