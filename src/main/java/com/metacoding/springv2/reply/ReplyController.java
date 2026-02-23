package com.metacoding.springv2.reply;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;
import com.metacoding.springv2.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * 댓글 관련 요청을 처리하는 컨트롤러입니다.
 * AuthController의 패턴을 참고하여 일관된 방식으로 작성되었습니다.
 */
@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;
    private final HttpSession session;

    /**
     * 댓글을 작성하는 API입니다.
     * 로그인한 사용자만 댓글을 달 수 있어요!
     * 
     * @param reqDTO 작성할 댓글 데이터
     * @return 성공 시 작성된 댓글 정보와 함께 200 OK를 반환해요.
     */
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO reqDTO) {
        // 세션에서 로그인한 유저 정보를 가져와요!
        User sessionUser = (User) session.getAttribute("sessionUser");
        
        // 서비스의 댓글쓰기 메서드를 호출하여 댓글을 저장합니다.
        var respDTO = replyService.댓글쓰기(reqDTO, sessionUser);
        
        return Resp.ok(respDTO);
    }

    /**
     * 특정 아이디를 가진 댓글을 수정하는 API입니다.
     * 
     * @param id 수정할 댓글 번호
     * @param reqDTO 수정할 데이터
     * @return 성공 시 수정된 댓글 데이터와 함께 200 OK를 반환해요.
     */
    @PutMapping("/api/replies/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ReplyRequest.UpdateDTO reqDTO) {
        // 세션에서 로그인한 유저 정보를 가져와요!
        User sessionUser = (User) session.getAttribute("sessionUser");
        
        // 서비스의 댓글수정 메서드를 호출합니다.
        var respDTO = replyService.댓글수정(id, reqDTO, sessionUser);
        
        return Resp.ok(respDTO);
    }

    /**
     * 특정 아이디를 가진 댓글을 삭제하는 API입니다.
     * 
     * @param id 삭제할 댓글 번호
     * @return 성공 시 200 OK와 함께 성공 메시지를 반환해요.
     */
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        // 세션에서 로그인한 유저 정보를 가져와요!
        User sessionUser = (User) session.getAttribute("sessionUser");
        
        // 서비스의 댓글삭제 메서드를 호출합니다.
        replyService.댓글삭제(id, sessionUser);
        
        return Resp.ok("댓글이 성공적으로 삭제되었습니다.");
    }

}
