package com.metacoding.springv2.board;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;
import com.metacoding.springv2.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    // 1. 게시글 목록보기
    @GetMapping
    public ResponseEntity<?> list() {
        var respDTO = boardService.게시글목록();
        return Resp.ok(respDTO);
    }

    // 2. 게시글 상세보기
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Integer id, @AuthenticationPrincipal User user) { // sessionUser임
        var respDTO = boardService.게시글상세(id, user.getId());
        return Resp.ok(respDTO);
    }

    // 3. 게시글 작성하기(body가 필요해)
    @PostMapping
    public ResponseEntity<?> save() {
        return Resp.ok(null);
    }

    // 4. 게시글 수정하기 (body가 필요해)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id) {
        return Resp.ok(null);
    }

    // 5. 게시글 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return Resp.ok(null);
    }
}