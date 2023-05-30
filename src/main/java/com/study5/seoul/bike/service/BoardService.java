package com.study5.seoul.bike.service;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;


    // 모든 게시글
    public List<Board> showBoardList(){
        return boardRepository.findAll();
    }

    // 삭제 게시물 제외 한 게시글 조회
    public List<Board> showNotDelBoardList(){
        return boardRepository.showNotDelBoardList();
    }

    // 제목 조회 게시글
    public List<Board> findByTitle(String title){
        return boardRepository.findByTitle(title);
    }

    //글 작성
    //멤버의 UUID 값을 게시물 값에 저장합니다.
    @Transactional
    public void create(String title,String content/*, Member member*/){
        /* 멤버가 없다면(로그인하지 않은 상태라면 예외를 발생해야 합니다.) >> 나중에
        *
        *  */
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        /*board.setUuid(Member.getUuid);*/
        board.setUpDate(LocalDateTime.now());
        board.setDelYN("N");
        boardRepository.save(board);
        log.info("created");

    }

    //글 수정
    @Transactional
    public void regBoard(Long boardId, String title, String content/*, Member member*/){
        //UUID Validation
/*      UUID boardUuid = boardRepository.fineOne(boardId).getUuid();
        UUID memberUuid = m0ember.getUuid();
        if(boardUuid != memberUuid){
            throw new IllegalStateException("작성자만 수정가능합니다.");}
*/
        Board board = boardRepository.fineOne(boardId);
        board.setTitle(title);
        board.setContent(content);
    }

    //글 삭제
    @Transactional
    public void delBoard(Long boardId/*, Member member*/) {
        //UUID Validation
/*      UUID boardUuid = boardRepository.fineOne(boardId).getUuid();
        UUID memberUuid = Member.getUuid();
        if(boardUuid != memberUuid){
            throw new IllegalStateException("작성자만 삭제가능합니다.");}
*/
        Board board = boardRepository.fineOne(boardId);
        board.setDelYN("Y");
    }
}
