package com.study5.seoul.bike.service;


import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Like;
import com.study5.seoul.bike.repository.BoardRepository;
import com.study5.seoul.bike.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.MemberRemoval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
//    private final MemberRepository memberRepository;

    //좋아요
    @Transactional
    public void doLike(Long id){
        Board board = boardRepository.findOne(id);
        Like like = new Like();
        like.setBoard(board);
        like.setFlag("Y");
        likeRepository.save(like);
    }

    //좋아요 취소
    @Transactional
    public void doNotLike(Long id){
        Board board = boardRepository.findOne(id);
        Like like = new Like();
        like.setBoard(board);
        like.setFlag("N");
        likeRepository.save(like);
    }

}
