package com.study5.seoul.bike.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimeFunction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue
    @Column(name = "commentId")
    private Long id;


    private UUID uuid;

    private String comment;

    private LocalDateTime upDate;

    //연관관계매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
