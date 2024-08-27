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
    private final String ADMIN_TOKEN = "admin";
    private final String MASTER_TOKEN = "master";

    @Transactional
    public void signUp(final SignUpRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();
        String phoneNumber = requestDto.getPhoneNumber();

        Optional<User> checkUserName = userRepository.findByUserName(userName);
        if(checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 존재");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(requestDto.isOwner()){
            role = UserRoleEnum.OWNER;
        }else if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }else if(requestDto.isMaster()){
            if(!MASTER_TOKEN.equals(requestDto.getMasterToken())) {
                throw new IllegalArgumentException("마스터 암호가 틀려 등록이 불가합니다.");
            }
            role = UserRoleEnum.MASTER;
        }

        User user = new User(userName, password, phoneNumber, role);
        userRepository.save(user);
    }

}
