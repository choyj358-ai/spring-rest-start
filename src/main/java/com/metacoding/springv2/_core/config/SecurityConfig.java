package com.metacoding.springv2._core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.metacoding.springv2._core.filter.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 필터 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 인증/권한 주소 커스터마이징
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**")
                .authenticated()
                .anyRequest()
                .permitAll()); // api가 붙은 주소는 다 통과

        // UsernamePasswordAuthenticationFilter
        // 폼 로그인 비활성화(POST : x-www-form-urlencoded : username, password)
        http.formLogin(f -> f.disable());

        // 베이직 인증 활성화 시킴 (request 할 때 마다 username, password를 요구) -> 비활성화
        http.httpBasic(b -> b.disable());

        // input에 crsf 토큰 받는 것을 비활성화하기
        http.csrf(c -> c.disable());

        // 인증 필터를 변경
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build(); // 리턴되는게 IoC에 등록된다
    }
}