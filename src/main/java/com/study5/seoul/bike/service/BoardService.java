package com.study5.seoul.bike.service;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Like;
import com.study5.seoul.bike.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

    // findOne
    public Board findOne(Long id){return boardRepository.findOne(id);}

    // 특정 게시글 조회(댓글과 함께)
    public Board findBoardWithComments(Long id) {return boardRepository.findBoardWithComments(id);}

    @Transactional
    // 모든 게시글 조회 ver 2 (댓글과 함께)
    public List<Board> findAllNotDeletedWithCommentsVer2(int offset,int limit) { return boardRepository.findAllNotDeletedWithCommentsVer2(offset,limit);}

    // 페이징 갯수 처리
    public int getTotalCount(){return boardRepository.getTotalCount();}

    // 모든 게시글 조회 (삭제 제외)
    public List<Board> findAllNotDeletedWithComments() {return boardRepository.findAllNotDeletedWithComments();}

    // 게시글 검색
    public List<Board> findByTitle(String title, int offset, int limit){return boardRepository.findByTitle(title, offset, limit);}

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
        UUID memberUuid = member.getUuid();
        if(boardUuid != memberUuid){
            throw new IllegalStateException("작성자만 수정가능합니다.");}
*/
        Board board = boardRepository.findOne(boardId);
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
        Board board = boardRepository.findOne(boardId);
        board.setDelYN("Y");
        //한번더 확인해서 수정된 것이 맞는지 후 처리.
        boardRepository.findOne(boardId).setDelYN("Y");
    }


    // 좋아요 갯수 긁어오기
    public List<Board> getLikeCount(Long id){
        return boardRepository.getLikeCount(id);
    }

}
