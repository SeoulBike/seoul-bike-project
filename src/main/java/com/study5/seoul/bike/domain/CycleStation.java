package com.study5.seoul.bike.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CycleStation extends BaseEntity {
    /*
    서울 공공자전거 대여소 정보의 내려오는 필드와 DB 칼럼명 일치시킴.

    "STA_LOC": "마포구",
    "RENT_ID": "ST-10",
    "RENT_NO": "00108",
    "RENT_NM": "서교동 사거리",
    "RENT_ID_NM": "108. 서교동 사거리",
    "HOLD_NUM": "10",
    "STA_ADD1": "서울특별시 마포구 양화로 93",
    "STA_ADD2": "427",
    "STA_LAT": "37.55274582",
    "STA_LONG": "126.91861725",
    "START_INDEX": 0,
    "END_INDEX": 0,
    "RNUM": "1"
     */

    @Column(name = "STA_LOC")
    private String district;

    private String rentId;
    private String rentNo;
    private String rentNm;
    private String rentIdNm;
//    @Column(name = "HOLD_NUM")
//    private Integer holdNum;

    @Column(name = "STA_ADD1")
    private String address1;
    @Column(name = "STA_ADD2")
    private String address2;
    @Column(name = "STA_LAT")
    private double lat;
    @Column(name = "STA_LONG")
    private double lnt;

}
