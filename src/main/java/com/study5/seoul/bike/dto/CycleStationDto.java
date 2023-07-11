package com.study5.seoul.bike.dto;

import com.google.gson.annotations.SerializedName;
import com.study5.seoul.bike.domain.CycleStation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CycleStationDto {

    Integer listTotalCount;
    @SerializedName("RESULT")
    CycleStationDto.Result result;
    @SerializedName("row")
    List<RowCycleStation> cycleStationDtos;

    public List<CycleStation> ListDtoToListEntity(List<RowCycleStation> rowCycleStations){
        return rowCycleStations.stream()
                .map(RowCycleStation::toEntity)
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
    public static class RowCycleStation {
        private String district;
        private String rentId;
        private String rentNo;
        private String rentNm;
        private String rentIdNm;
        private String address1;
        private String address2;
        private double lat;
        private double lnt;

        public CycleStation toEntity() {
            return CycleStation.builder()
                    .district(this.district)
                    .rentId(this.rentId)
                    .rentNm(this.rentNm)
                    .rentNo(this.rentNo)
                    .rentIdNm(this.rentIdNm)
                    .address1(this.address1)
                    .address2(this.address2)
                    .lat(this.lat)
                    .lnt(this.lnt)
                    .build();
        }
    }

}
