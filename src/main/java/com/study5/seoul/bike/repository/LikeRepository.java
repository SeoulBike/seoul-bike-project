package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private final EntityManager em;

    public void save(Like like){
        em.persist(like);
    }

    public void doLike(Like like){
        like.setFlag("Y");
    }

    public void dontLike(Like like){
        like.setFlag("N");
    }
}
