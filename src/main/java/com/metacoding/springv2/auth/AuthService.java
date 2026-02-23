package com.metacoding.springv2.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception401;
import com.metacoding.springv2._core.util.JwtUtil;
import com.metacoding.springv2.auth.AuthRequest.JoinDTO;
import com.metacoding.springv2.auth.AuthRequest.LoginDTO;
import com.metacoding.springv2.user.User;
import com.metacoding.springv2.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 비즈니스 로직을 처리하는 서비스입니다.
 * 클래스 상단에 전역적으로 readOnly 트랜잭션을 적용했어요!
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 로그인을 처리합니다.
     * 
     * @param reqDTO 로그인 요청 데이터
     * @return 생성된 JWT 토큰
     */
    public String 로그인(LoginDTO reqDTO) {

        // 1. UserRepository에서 username 확인
        User findUser = userRepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new Exception401("유저네임을 찾을 수 없어요"));

        // 2. password를 hash해서 비교검증
        boolean isSamePassword = bCryptPasswordEncoder.matches(reqDTO.getPassword(), findUser.getPassword());
        if (!isSamePassword)
            throw new Exception401("비밀번호가 틀렸어요");

        return JwtUtil.create(findUser);
    }

    /**
     * 회원가입을 처리합니다.
     * 데이터 저장이 필요하므로 별도의 @Transactional을 사용했어요!
     * 
     * @param reqDTO 회원가입 요청 데이터
     * @return 가입된 유저 정보 DTO
     */
    @Transactional
    public AuthResponse.DTO 회원가입(JoinDTO reqDTO) {

        // 1. 패스워드 해시하기
        String encPassword = bCryptPasswordEncoder.encode(reqDTO.getPassword());

        User user = User.builder()
                .username(reqDTO.getUsername())
                .password(encPassword)
                .email(reqDTO.getEmail())
                .roles("USER")
                .build();
        userRepository.save(user);
        return new AuthResponse.DTO(user);
    }

    /**
     * 유저네임 중복 체크를 수행합니다.
     * 
     * @param reqDTO 중복 체크 요청 데이터
     * @return 중복 여부 결과 DTO
     */
    public AuthResponse.CheckUsernameDTO 유저네임중복체크(AuthRequest.CheckUsernameDTO reqDTO) {
        Optional<User> user = userRepository.findByUsername(reqDTO.getUsername());
        if (user.isPresent()) {
            return new AuthResponse.CheckUsernameDTO(false, "이미 사용중인 유저네임입니다.");
        } else {
            return new AuthResponse.CheckUsernameDTO(true, "사용가능한 유저네임입니다.");
        }
    }
}
