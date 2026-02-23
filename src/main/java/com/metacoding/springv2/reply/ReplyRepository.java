package com.metacoding.springv2.reply;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글 레포지토리
 * JpaRepository를 상속받아 기본 CRUD 기능을 사용합니다.
 */
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
