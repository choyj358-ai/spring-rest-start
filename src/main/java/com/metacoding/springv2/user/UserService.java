package com.metacoding.springv2.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception404;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 정보와 관련된 비즈니스 로직을 처리하는 서비스입니다.
 * 클래스 상단에 전역적으로 readOnly 트랜잭션을 적용했어요!
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 특정 아이디를 가진 사용자의 정보를 조회합니다.
     * 
     * @param id 사용자 아이디
     * @return 사용자의 상세 정보 DTO
     * @throws Exception404 해당 유저를 찾지 못했을 경우 발생해요!
     */
    public UserResponse.UserFindByIdDTO 회원정보보기(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception404("조회하려는 사용자를 찾을 수 없습니다."));
        return new UserResponse.UserFindByIdDTO(user);
    }
}
