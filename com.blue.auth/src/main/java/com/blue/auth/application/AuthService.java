package com.blue.auth.application;

import com.blue.auth.application.dtos.AuthResponseDto;
import com.blue.auth.application.dtos.SignUpRequestDto;
import com.blue.auth.domain.User;
import com.blue.auth.domain.UserRepository;
import com.blue.auth.domain.UserRoleEnum;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final SecretKey secretKey;

    public AuthService(UserRepository userRepository, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    // 임시로 정해둔 Key 값
    private final String ADMIN_TOKEN = "ADMIN";
    private final String MASTER_TOKEN = "MASTER";

    @Transactional
    public void signUp(final SignUpRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();
        String phoneNumber = requestDto.getPhoneNumber();

        Optional<User> checkUserName = userRepository.findByUserName(userName);
        if(checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 존재");
        }


    }

}
