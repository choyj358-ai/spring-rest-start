package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception404;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // id, title
    @Transactional(readOnly = true)
    public List<BoardResponse.List> 게시글목록() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> new BoardResponse.List(board))
                .toList();
    }

    // tr=read, osiv=f, lazy전략
    // id, title, content, userId, username
    @Transactional(readOnly = true)
    public BoardResponse.Detail 게시글상세(int id, int sessionUserId) {
        Board board = boardRepository.findByIdWithUser(id)
                .orElseThrow(() -> new Exception404("자원을 찾을 수 없어요"));
        return new BoardResponse.Detail(board, sessionUserId);

    }

    @Transactional
    public void 게시글작성() {
    }

    @Transactional
    public void 게시글수정() {
    }

    @Transactional
    public void 게시글삭제() {
    }

}