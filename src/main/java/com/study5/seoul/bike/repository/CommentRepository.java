package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public Comment fineOne(Long id){
        return em.find(Comment.class,id);
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment b",Comment.class).getResultList();
    }

    // 모든 댓글 중 삭제 제외한 게시물들 조회
    public List<Comment> showNotDelCommentList(){
        return em.createQuery("select c from Comment c where c.DelYN = :DelYN",Comment.class)
                .setParameter("DelYN","N")
                .getResultList();
    }
}
