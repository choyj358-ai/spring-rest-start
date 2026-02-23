package com.metacoding.springv2.board;

import lombok.Data;

/**
 * 게시글 관련 요청 데이터를 담는 클래스입니다.
 */
public class BoardRequest {

    /**
     * 게시글 작성을 위한 DTO입니다.
     * 제목과 내용을 전달받아요!
     */
    @Data
    public static class SaveDTO {
        private String title;
        private String content;
    }

    /**
     * 게시글 수정을 위한 DTO입니다.
     * 제목과 내용을 클라이언트로부터 전달받아요!
     */
    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
    }
}
