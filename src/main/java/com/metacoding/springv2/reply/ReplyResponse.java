package com.metacoding.springv2.reply;

import lombok.Data;

/**
 * 댓글 관련 응답 데이터를 담는 클래스입니다.
 */
public class ReplyResponse {

    /**
     * 댓글 작성 후 반환할 응답 DTO입니다.
     * 작성된 댓글 번호와 내용, 작성자 및 게시글 정보를 포함해요!
     */
    @Data
    public static class SaveDTO {
        private Integer id;
        private String comment;
        private Integer userId;
        private String username;
        private Integer boardId;

        public SaveDTO(Reply reply) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            if (reply.getUser() != null) {
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
            }
            if (reply.getBoard() != null) {
                this.boardId = reply.getBoard().getId();
            }
        }
    }
}
