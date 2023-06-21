package com.study5.seoul.bike.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimeFunction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Comment {
    @Id @GeneratedValue
    @Column(name = "commentId")
    private Long id;

    // 연관관계 매핑 나중에
    // private Member member

    private UUID uuid;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime upDate;

    private String DelYN;

    //연관관계매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
