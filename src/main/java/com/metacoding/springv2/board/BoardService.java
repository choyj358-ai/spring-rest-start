package com.metacoding.springv2.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 서비스
 * 게시글 관련 비즈니스 로직을 처리합니다.
 */
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 목록을 조회합니다.
     * DB에서 모든 게시글을 가져와 ListDTO로 변환하여 반환합니다.
     * 
     * @return List<BoardResponse.ListDTO>
     */
    @Transactional(readOnly = true)
    public List<BoardResponse.ListDTO> 게시글목록() {
        return boardRepository.findAll().stream()
                .map(BoardResponse.ListDTO::new)
                .collect(Collectors.toList());
    }
}
