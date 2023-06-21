package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Like;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board findOne(Long id){
        return em.find(Board.class,id);
    }


    //검색기능 + 검색된 리스트 반환 + 댓글과 함께 + 페이징
    public List<Board> findByTitle(String title, int offset, int limit) {
        String jpql = "SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.DelYN = :DelYN AND b.title LIKE CONCAT('%', :title, '%') ORDER BY b.id DESC";
        return em.createQuery(jpql, Board.class)
                .setParameter("DelYN", "N")
                .setParameter("title", title)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    //OUTER 조인 찾아볼것
    //특정 게시글 댓글과 함께 조회
    public Board findBoardWithComments(Long id){
        String jpql = "SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments c WHERE b.id = :boardId AND c.DelYN = :DelYN";
        return em.createQuery(jpql,Board.class)
                .setParameter("DelYN","N")
                .setParameter("boardId",id)
                .getSingleResult();
    }
    // 모든 게시글 조회 (삭제 제외) ver.2 >> 이걸 왜 쓰느냐~? >> 페이징 처리를 하기 위해서
    public List<Board> findAllNotDeletedWithCommentsVer2(int offset, int limit) {
        String jpql = "SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.DelYN = :delYN ORDER BY b.id DESC";
        return em.createQuery(jpql, Board.class)
                .setParameter("delYN", "N")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    // 모든 게시글 조회 (삭제 제외)
    public List<Board> findAllNotDeletedWithComments() {
        String jpql = "SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.DelYN = :delYN ORDER BY b.id DESC";
        return em.createQuery(jpql, Board.class)
                .setParameter("delYN", "N")
                .getResultList();
    }

    //좋아요 갯수 긁어오기
    public List<Board> getLikeCount(Long id) {
        String jpql = "SELECT b FROM Board b LEFT JOIN FETCH b.likes l WHERE b.id = :boardId AND l.flag = 'Y'";
        return em.createQuery(jpql, Board.class)
                .setParameter("boardId", id)
                .getResultList();

    }
    // 게시물 총 갯수 가져오기
    public int getTotalCount() {
        Long count = em.createQuery("SELECT COUNT(DISTINCT b) FROM Board b LEFT JOIN b.comments c WHERE b.DelYN = :delYN", Long.class)
                .setParameter("delYN", "N")
                .getSingleResult();
        return count.intValue();
    }

}
