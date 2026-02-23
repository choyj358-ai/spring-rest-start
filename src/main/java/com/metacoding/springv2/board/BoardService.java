package com.metacoding.springv2.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception404;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 계층입니다.
 * 클래스 전체에 전역적으로 readOnly 트랜잭션을 적용했어요!
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 새로운 게시글을 작성합니다.
     * 데이터 저장이 필요하므로 별도의 @Transactional을 사용했어요!
     * 
     * @param reqDTO 작성할 게시글 데이터
     * @param sessionUser 작성자 정보
     * @return 작성된 게시글 상세 응답 DTO
     */
    @Transactional
    public BoardResponse 게시글쓰기(BoardRequest.SaveDTO reqDTO, com.metacoding.springv2.user.User sessionUser) {
        // 게시글 엔티티를 생성하여 저장해요!
        Board board = Board.builder()
                .title(reqDTO.getTitle())
                .content(reqDTO.getContent())
                .user(sessionUser)
                .build();
        
        boardRepository.save(board);
        
        return new BoardResponse(board);
    }

    /**
     * DB에서 모든 게시글 데이터를 가져와 목록용 DTO 리스트로 변환합니다.
     * stream API 사용 시 람다식을 사용하도록 수정했어요!
     * 
     * @return BoardListDTO 객체들의 리스트
     */
    public List<BoardListDTO> 게시글목록() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardListDTO(board))
                .collect(Collectors.toList());
    }

    /**
     * 특정 아이디를 가진 게시글의 상세 정보를 조회합니다.
     * 
     * @param id 게시글 아이디
     * @return 성공 시 BoardResponse 객체를, 실패 시 Exception404를 반환합니다.
     */
    public BoardResponse 게시글상세보기(Integer id) {
        Board board = boardRepository.findByIdFetchUser(id)
                .orElseThrow(() -> new Exception404("조회하려는 게시글을 찾을 수 없습니다."));
        
        return new BoardResponse(board);
    }

    /**
     * 게시글의 제목과 내용을 수정합니다.
     * 데이터 변경이 일어나므로 별도의 @Transactional을 사용했어요!
     * 
     * @param id 수정할 게시글 아이디
     * @param reqDTO 수정할 데이터가 담긴 DTO
     * @return 수정된 결과 정보를 담은 BoardResponse DTO
     */
    @Transactional
    public BoardResponse 게시글수정(Integer id, BoardRequest.UpdateDTO reqDTO) {
        // 수정할 게시글을 먼저 찾습니다.
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("수정하려는 게시글을 찾을 수 없습니다."));
        
        // 엔티티의 update 메서드를 호출하여 데이터를 변경해요! (더티 체킹 발생)
        board.update(reqDTO.getTitle(), reqDTO.getContent());
        
        return new BoardResponse(board);
    }

    /**
     * 특정 아이디를 가진 게시글을 삭제합니다.
     * 데이터 삭제가 수반되므로 @Transactional을 붙여주었어요!
     * 
     * @param id 삭제할 게시글 번호
     */
    @Transactional
    public void 게시글삭제(Integer id) {
        // 먼저 삭제할 게시글이 존재하는지 확인합니다.
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("삭제하려는 게시글을 찾을 수 없습니다."));
        
        // 게시글을 삭제해요!
        boardRepository.delete(board);
    }

}
