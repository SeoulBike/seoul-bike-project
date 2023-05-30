package com.study5.seoul.bike.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
public class BikeStationMasterDto {

    Integer listTotalCount;
    @SerializedName("RESULT")
    BikeStationMasterDto.Result result;
    @SerializedName("row")
    List<BikeStationMasterDto.BikeStationMaster> bikeStationMasters;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Result {
        String code;
        String message;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BikeStationMaster {
        private String id;
        private String address1;
        private String address2;
        private double lat;
        private double lnt;
    }

}
