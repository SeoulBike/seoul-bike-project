package com.study5.seoul.bike.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Like {

    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private Long id;

    private UUID uuid;

    private String flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
