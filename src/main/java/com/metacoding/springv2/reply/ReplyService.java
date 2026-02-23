package com.metacoding.springv2.reply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception404;
import com.metacoding.springv2.board.Board;
import com.metacoding.springv2.board.BoardRepository;
import com.metacoding.springv2.user.User;

import lombok.RequiredArgsConstructor;

/**
 * 댓글 관련 비즈니스 로직을 처리하는 서비스 계층입니다.
 * 클래스 상단에 전역적으로 readOnly 트랜잭션을 적용했어요!
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글을 작성합니다.
     * 데이터 저장이 필요하므로 별도의 @Transactional을 사용했어요!
     * 
     * @param reqDTO 작성할 댓글 데이터
     * @param sessionUser 작성자 정보
     * @return 작성된 댓글 정보 응답 DTO
     */
    @Transactional
    public ReplyResponse.SaveDTO 댓글쓰기(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        // 댓글을 달 게시글을 먼저 찾습니다.
        Board board = boardRepository.findById(reqDTO.getBoardId())
                .orElseThrow(() -> new Exception404("조회하려는 게시글을 찾을 수 없습니다."));

        // 댓글 엔티티를 생성하여 저장해요!
        Reply reply = Reply.builder()
                .comment(reqDTO.getComment())
                .board(board)
                .user(sessionUser)
                .build();
        
        replyRepository.save(reply);
        
        return new ReplyResponse.SaveDTO(reply);
    }

    /**
     * 댓글 내용을 수정합니다.
     * 데이터 변경이 필요하므로 별도의 @Transactional을 사용했어요!
     * 
     * @param id 수정할 댓글 아이디
     * @param reqDTO 수정할 데이터가 담긴 DTO
     * @param sessionUser 현재 로그인한 사용자 (권한 확인용)
     * @return 수정된 결과 정보를 담은 응답 DTO
     */
    @Transactional
    public ReplyResponse.SaveDTO 댓글수정(Integer id, ReplyRequest.UpdateDTO reqDTO, User sessionUser) {
        // 수정할 댓글을 먼저 찾습니다.
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new Exception404("수정하려는 댓글을 찾을 수 없습니다."));

        // 권한 확인: 댓글 작성자만 수정할 수 있어요!
        if (!reply.getUser().getId().equals(sessionUser.getId())) {
            throw new com.metacoding.springv2._core.handler.ex.Exception403("댓글을 수정할 권한이 없습니다.");
        }

        // 엔티티의 update 메서드를 호출하여 데이터를 변경해요! (더티 체킹 활용)
        reply.update(reqDTO.getComment());
        
        return new ReplyResponse.SaveDTO(reply);
    }

    /**
     * 특정 아이디를 가진 댓글을 삭제합니다.
     * 데이터 삭제가 수반되므로 @Transactional을 붙여주었어요!
     * 
     * @param id 삭제할 댓글 번호
     * @param sessionUser 현재 로그인한 사용자 (권한 확인용)
     */
    @Transactional
    public void 댓글삭제(Integer id, User sessionUser) {
        // 삭제할 댓글을 먼저 찾습니다.
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new Exception404("삭제하려는 댓글을 찾을 수 없습니다."));

        // 권한 확인: 댓글 작성자만 삭제할 수 있어요!
        if (!reply.getUser().getId().equals(sessionUser.getId())) {
            throw new com.metacoding.springv2._core.handler.ex.Exception403("댓글을 삭제할 권한이 없습니다.");
        }

        // 댓글을 삭제해요!
        replyRepository.delete(reply);
    }

}
