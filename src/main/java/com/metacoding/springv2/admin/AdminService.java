package com.metacoding.springv2.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 관리자 전용 비즈니스 로직을 처리하는 서비스 계층입니다.
 * 클래스 상단에 전역적으로 readOnly 트랜잭션을 적용했어요!
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdminService {
    
}
