package com.metacoding.springv2.user;

import org.springframework.stereotype.Service;

import com.metacoding.springv2._core.handler.ex.Exception404;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 정보 보기
     * 
     * @param id
     * @return
     * @throws Exception404 해당 유저를 찾지 못했을 경우
     */
    public UserResponse.UserFindByIdDTO 회원정보보기(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다"));
        return new UserResponse.UserFindByIdDTO(user);
    }
}