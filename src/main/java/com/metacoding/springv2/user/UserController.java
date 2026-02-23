package com.metacoding.springv2.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 조회 API
     * 
     * @param id
     * @return
     */
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") Integer id) {
        UserResponse.UserFindByIdDTO respDTO = userService.회원정보보기(id);
        return Resp.ok(respDTO);
    }
}

