package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board fineOne(Long id){
        return em.find(Board.class,id);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b",Board.class).getResultList();
    }

    public List<Board> findByTitle(String title){
        return em.createQuery("select b from Board b where b.title = :title",Board.class)
                .setParameter("title",title)
                .getResultList();
    }
    // 모든 게시글 중 삭제 제외한 게시물들 조회
    public List<Board> showNotDelBoardList(){
        return em.createQuery("select b from Board b where b.DelYN = :DelYN",Board.class)
                .setParameter("DelYN","N")
                .getResultList();
    }

}
