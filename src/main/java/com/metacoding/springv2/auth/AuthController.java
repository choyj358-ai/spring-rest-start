package com.metacoding.springv2.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AuthRequest.JoinDTO reqDTO) {
        var respDTO = authService.회원가입(reqDTO);
        return Resp.ok(respDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest.LoginDTO reqDTO) {

        String accessToken = authService.로그인(reqDTO);

        return Resp.ok(accessToken);
    }

    /**
     * 유저네임 중복 체크 API
     * 
     * @param reqDTO
     * @return
     */
    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody AuthRequest.CheckUsernameDTO reqDTO) {
        AuthResponse.CheckUsernameDTO respDTO = authService.유저네임중복체크(reqDTO);
        return Resp.ok(respDTO);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "health ok";
    }
}
