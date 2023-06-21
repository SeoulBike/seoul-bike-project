package com.study5.seoul.bike.service;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Comment;
import com.study5.seoul.bike.repository.BoardRepository;
import com.study5.seoul.bike.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
//    private final MemberRepository memberRepository;

    public Comment findOne(Long id){
        return commentRepository.fineOne(id);
    }
    // 삭제 제외한 모든 댓글 조회
    public List<Comment> showNotDelCommentsList(){
        return commentRepository.showNotDelCommentList();
    }


    //댓글 작성
    @Transactional
    public void create(Long boardId,String content     /*, Member member*/){
        Board board = boardRepository.findOne(boardId);
        Comment comment = new Comment();
        //UUID 설정
        // comment.setUuid(member.getUuid);
        comment.setBoard(board);
        comment.setContent(content);
        comment.setDelYN("N");
        comment.setUpDate(LocalDateTime.now());
        commentRepository.save(comment);
    }


    //댓글 수정
    @Transactional
    public void regComment(Long CommentId, String content    /*, Member member*/){
        Comment comment = commentRepository.fineOne(CommentId);
        //UUID Validation
/*
        UUid memberUuid = member.getUuid();
        Uuid commentUuid = comment.getUuid();
        if(MemberUuid != commentUuid)
            throw new IllegalStateException("작성자만 수정가능합니다.");}
 */

        comment.setContent(content);
    }



    //댓글 삭제
    @Transactional
    public void delComment(Long CommentId /*, Member member */){
        Comment comment = commentRepository.fineOne(CommentId);
        //UUID Validation
/*
        UUid memberUuid = member.getUuid();
        Uuid commentUuid = comment.getUuid();
        if(MemberUuid != commentUuid)
            throw new IllegalStateException("작성자만 삭제가능합니다.");}
 */

        comment.setDelYN("Y");

        //한번 더 삭제 확인하는 습관
        commentRepository.fineOne(CommentId);
        comment.setDelYN("Y");

    }
}
