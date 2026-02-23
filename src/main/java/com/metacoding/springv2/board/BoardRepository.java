package com.metacoding.springv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시글 레포지토리
 * JpaRepository를 상속받아 기본 CRUD 기능을 사용합니다.
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {
    
}
