package com.metacoding.springv2.board;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

/**
 * 게시글 상세 응답을 위한 DTO입니다.
 * (id, title, content, userId, username, replies) 정보를 포함하며, 댓글 목록도 함께 담아요!
 */
@Data
public class BoardResponse {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String username;
    private List<ReplyDTO> replies; // 게시글에 달린 댓글 리스트

    /**
     * Board 엔티티를 받아서 DTO로 변환해주는 생성자입니다.
     * 댓글 목록도 함께 변환하여 담아주었어요!
     * 
     * @param board 게시글 엔티티
     */
    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        
        // 작성자 정보가 있을 때만 아이디와 이름을 채워넣어요
        if (board.getUser() != null) {
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
        }

        // 댓글 목록을 ReplyDTO 리스트로 변환해요! (람다식 사용)
        this.replies = board.getReplies().stream()
                .map(reply -> new ReplyDTO(reply))
                .collect(Collectors.toList());
    }

    /**
     * 댓글 정보를 담기 위한 내부 DTO입니다.
     * (id, comment, userId, username) 정보를 포함해요.
     */
    @Data
    public static class ReplyDTO {
        private Integer id;
        private String comment;
        private Integer userId;
        private String username;

        public ReplyDTO(com.metacoding.springv2.reply.Reply reply) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            if (reply.getUser() != null) {
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
            }
        }
    }
}
