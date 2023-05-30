package com.study5.seoul.bike.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "likeId")
    private Long id;

    private UUID uuid;

    private String flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
