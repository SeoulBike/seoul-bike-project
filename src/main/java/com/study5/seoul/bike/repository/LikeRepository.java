package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private final EntityManager em;

    public void save(Like like){
        em.persist(like);
    }

    //좋아요 갯수 긁어오기
    public int getLikeCount(Long id) {
        String jpql = "SELECT COUNT(l) FROM Board b JOIN b.likes l WHERE b.id = :boardId AND l.flag = 'Y'";
        return em.createQuery(jpql, Long.class)
                .setParameter("boardId", id)
                .getSingleResult()
                .intValue();

    }

}
