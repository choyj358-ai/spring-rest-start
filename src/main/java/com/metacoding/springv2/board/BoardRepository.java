package com.metacoding.springv2.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 게시글 레포지토리
 * JpaRepository를 상속받아 기본 CRUD 기능을 사용합니다.
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {

    /**
     * 게시글 상세 조회 (유저 정보 및 댓글 목록 포함)
     * 페치 조인을 사용하여 N+1 문제를 방지하고 한 번의 쿼리로 데이터를 가져옵니다.
     * 댓글(replies)과 댓글의 작성자(r.user)까지 모두 가져오도록 개선했어요!
     * 
     * @param id 게시글 ID
     * @return Optional<Board>
     */
    @Query("select b from Board b left join fetch b.user left join fetch b.replies r left join fetch r.user where b.id = :id")
    Optional<Board> findByIdFetchUser(@Param("id") Integer id);
}
