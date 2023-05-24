package com.study5.seoul.bike.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
public class Board {

    //Board 엔티티의 id(pk값)
    //생성은 자동 Column명은 camel로 통일.
    @Id @GeneratedValue
    @Column(name = "boardId")
    private Long id;

    //uuid 해당 uuid 유효성 검사를 위한 컬럼
    private UUID uuid;

    //제목, 제목은 null이 될 수 없음.
    @Column(nullable = false)
    private String title;

    //내용, 내용도 null이 될 수 없음.
    @Column(nullable = false)
    private String content;


    //작성일시, 페이징 처리를 위하여 sort할 것이기 때문에 필요함.
    @CreatedDate
    private LocalDateTime upDate;

    /* 수정일시는 필요 없을 것 같다. 왜냐면 페이징 처리할 때 작성 일시로만 하니깐 딱히 필요 없으니 pass! */


    //연관관계 매핑
    // Member <-> Board N대N 즉, 다대다가 되어야 합니다. 다대다 매핑보다는 새로운 테이블을 만들어서
    // 1대 N / N대 1을 하는 것이 무조건 적으로 좋다는 강의를 들어서 이 부분은 Member를 만드는 유진님과 상의 해보고 구현하겠습니다.


    //연관관계 매핑, fk를 가진 comment에 갑을 두어줍니다. 을은 mappedBy로 연결해줍니다.
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    //연관관계 매핑, fk를 가진 like에 갑을 두어줍니다. 을은 mappedBy로 연결해줍니다.
    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>();

}
