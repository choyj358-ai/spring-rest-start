package com.metacoding.springv2.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("select b from Board b join fetch b.user where b.id = :id")
    public Optional<Board> findByIdWithUser(@Param("id") int id);
}