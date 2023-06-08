package com.study5.seoul.bike.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BikeStation {

    @Id
    @Column(name = "LENDPLACE_ID")
    private String id;

    @Column(name = "STATN_ADDR1")
    private String address1;
    @Column(name = "STATN_ADDR2")
    private String address2;

    @Column(name = "STATN_LAT")
    private double lat;
    @Column(name = "STATN_LNT")
    private double lnt;

}
