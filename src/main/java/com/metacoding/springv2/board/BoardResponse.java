package com.metacoding.springv2.board;

import lombok.Data;

/**
 * 게시글 응답 DTO
 * 게시글 관련 API에서 반환할 데이터를 정의합니다.
 */
public class BoardResponse {

    /**
     * 게시글 목록 조회를 위한 DTO
     * (id, title, content) 정보를 포함합니다.
     */
    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String content;

        public ListDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
}
