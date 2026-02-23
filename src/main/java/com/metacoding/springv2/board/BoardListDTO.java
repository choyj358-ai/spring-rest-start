package com.metacoding.springv2.board;

import lombok.Data;

/**
 * 게시글 목록 보기 응답을 위한 DTO입니다.
 * 목록에서 보여줄 핵심 정보인 번호, 제목, 내용을 담고 있어요!
 */
@Data
public class BoardListDTO {
    private Integer id;
    private String title;
    private String content;

    /**
     * Board 엔티티를 목록용 DTO로 변환해줍니다.
     * 
     * @param board 게시글 엔티티
     */
    public BoardListDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
