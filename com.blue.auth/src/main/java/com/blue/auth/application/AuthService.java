package com.blue.auth.application;

import com.blue.auth.application.dtos.LogInRequestDto;
import com.blue.auth.application.dtos.SignUpRequestDto;
import com.blue.auth.application.dtos.UpdateRequestDto;
import com.blue.auth.domain.User;
import com.blue.auth.domain.UserRepository;
import com.blue.auth.domain.UserRoleEnum;
import com.blue.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        String token = requestDto.getToken();

        Optional<User> checkUserName = userRepository.findByUserNameAndDeletedAtIsNull(userName);
        if(checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 존재");
        }

        if(!token.isEmpty()){
            if(token.equals(ADMIN_TOKEN)&&role.equals(UserRoleEnum.ADMIN)) {
                role = UserRoleEnum.ADMIN;
            }else if(token.equals(MASTER_TOKEN)&&role.equals(UserRoleEnum.MASTER)) {
                role = UserRoleEnum.MASTER;
            }else {
                throw new IllegalArgumentException("권한 토큰이 일치하지 않습니다.");
            }
        }

        User user = new User(userName, phoneNumber, password, role);
        userRepository.save(user);
    }

    public void logIn(LogInRequestDto requestDto, HttpServletResponse response){
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        User user = userRepository.findByUserNameAndDeletedAtIsNull(userName).orElseThrow(
                ()->new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getUserName());
        jwtUtil.addJwtToCookie(token, response);
    }

    @Transactional
    public void userEdit(String userName, UpdateRequestDto requestDto){
        String phoneNumber = requestDto.getPhoneNumber();
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = userRepository.findByUserNameAndDeletedAtIsNull(userName).orElseThrow(
                ()-> new IllegalArgumentException("수정하려는 사용자 정보가 없습니다.")
        );

        user.userUpdate(phoneNumber, password);
        userRepository.save(user);
    }

    @Transactional
    public void userWithdraw(String userName){
        User user = userRepository.findByUserNameAndDeletedAtIsNull(userName).orElseThrow(
                ()-> new IllegalArgumentException("이미 탈퇴한 사용자 입니다.")
        );

        user.setDeleted(LocalDateTime.now(), userName);
        userRepository.save(user);
    }

    public String getAuthority(String userName){
        User user = userRepository.findByUserNameAndDeletedAtIsNull(userName).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        String role = user.getRole().getAuthority();
        return role;
    }

}
