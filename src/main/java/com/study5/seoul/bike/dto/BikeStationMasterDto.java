package com.study5.seoul.bike.dto;

import com.google.gson.annotations.SerializedName;
import com.study5.seoul.bike.domain.BikeStation;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BikeStationMasterDto {

    Integer listTotalCount;
    @SerializedName("RESULT")
    BikeStationMasterDto.Result result;
    @SerializedName("row")
    List<BikeStationDto> bikeStationDtos;

    public List<BikeStation> ListDtoToListEntity(List<BikeStationDto> bikeStationDtos){
        return bikeStationDtos.stream()
                .map(BikeStationDto::toEntity)
                .collect(Collectors.toList());
    }


    @Getter
    @Builder
    public static class Result {
        String code;
        String message;
    }

    @Getter
    @Builder
    public static class BikeStationDto {
        private String id;
        private String address1;
        private String address2;
        private double lat;
        private double lnt;

        public BikeStation toEntity() {
            return BikeStation.builder()
                    .id(this.id)
                    .address1(this.address1)
                    .address2(this.address2)
                    .lat(this.lat)
                    .lnt(this.lnt)
                    .build();
        }
    }

}
