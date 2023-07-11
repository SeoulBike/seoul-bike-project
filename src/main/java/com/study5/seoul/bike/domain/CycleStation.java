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

    @Column(name = "STA_LOC")
    private String district;

    private String rentId;
    private String rentNo;
    private String rentNm;
    private String rentIdNm;

    @Column(name = "STA_ADD1")
    private String address1;
    @Column(name = "STA_ADD2")
    private String address2;
    @Column(name = "STA_LAT")
    private double lat;
    @Column(name = "STA_LONG")
    private double lnt;

}
