package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 컨트롤러
 * 게시글 관련 API를 제공합니다.
 */
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 목록 보기 API
     * 
     * @return ResponseEntity<List<BoardResponse.ListDTO>>
     */
    @GetMapping("/api/boards")
    public ResponseEntity<?> findAll() {
        List<BoardResponse.ListDTO> respDTOs = boardService.게시글목록();
        return Resp.ok(respDTOs);
    }
}
