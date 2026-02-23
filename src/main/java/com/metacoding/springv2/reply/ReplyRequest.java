package com.metacoding.springv2.reply;

import lombok.Data;

/**
 * 댓글 관련 요청 데이터를 담는 클래스입니다.
 */
public class ReplyRequest {

    /**
     * 댓글 작성을 위한 DTO입니다.
     * 댓글 내용과 해당 게시글의 번호를 전달받아요!
     */
    @Data
    public static class SaveDTO {
        private String comment;
        private Integer boardId;
    }

    /**
     * 댓글 수정을 위한 DTO입니다.
     * 수정할 댓글 내용을 전달받아요!
     */
    @Data
    public static class UpdateDTO {
        private String comment;
    }
}
