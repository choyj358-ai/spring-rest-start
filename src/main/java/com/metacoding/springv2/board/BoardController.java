package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 관련 요청을 처리하는 컨트롤러입니다.
 * AuthController의 패턴을 참고하여 일관된 방식으로 작성되었습니다.
 */
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final jakarta.servlet.http.HttpSession session;

    /**
     * 새로운 게시글을 작성하는 API입니다.
     * 로그인한 사용자만 글을 쓸 수 있어요!
     * 
     * @param reqDTO 작성할 게시글 데이터
     * @return 성공 시 작성된 게시글 정보와 함께 200 OK를 반환해요.
     */
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO) {
        // 세션에서 로그인한 유저 정보를 가져와요!
        com.metacoding.springv2.user.User sessionUser = (com.metacoding.springv2.user.User) session.getAttribute("sessionUser");
        
        // 서비스의 게시글쓰기 메서드를 호출하여 게시글을 저장합니다.
        var respDTO = boardService.게시글쓰기(reqDTO, sessionUser);
        
        return Resp.ok(respDTO);
    }

    /**
     * 게시글 전체 목록을 가져오는 API입니다.
     * 
     * @return 성공 시 게시글 목록 데이터와 함께 200 OK를 반환해요.
     */
    @GetMapping("/api/boards")
    public ResponseEntity<?> findAll() {
        var respDTO = boardService.게시글목록();
        return Resp.ok(respDTO);
    }

    /**
     * 특정 게시글의 상세 정보를 가져오는 API입니다.
     * 
     * @param id 조회할 게시글의 고유 번호
     * @return 성공 시 게시글 상세 데이터와 함께 200 OK를 반환해요.
     */
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        var respDTO = boardService.게시글상세보기(id);
        return Resp.ok(respDTO);
    }

    /**
     * 특정 아이디를 가진 게시글을 수정하는 API입니다.
     * 
     * @param id 수정할 게시글 번호
     * @param reqDTO 수정할 데이터
     * @return 성공 시 수정된 게시글 데이터와 함께 200 OK를 반환해요.
     */
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO) {
        var respDTO = boardService.게시글수정(id, reqDTO);
        return Resp.ok(respDTO);
    }

    /**
     * 특정 아이디를 가진 게시글을 삭제하는 API입니다.
     * 
     * @param id 삭제할 게시글 번호
     * @return 성공 시 200 OK와 함께 성공 메시지를 반환해요.
     */
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        boardService.게시글삭제(id);
        return Resp.ok("게시글이 성공적으로 삭제되었습니다.");
    }

}
