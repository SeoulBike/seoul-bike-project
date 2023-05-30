package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.study5.seoul.bike.dto.BikeStationMasterDto;
import com.study5.seoul.bike.repository.BikeStationMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeStationMasterService {

    @Value("${api.url}")
    private String baseUrl;

    @Value("${api.bikeMaster.key}")
    private String key;

    @Value("${api.bikeMaster.type}")
    private String type;

    @Value("${api.bikeMaster.service}")
    private String service;


    private final BikeStationMasterRepository bikeStationMasterRepository;

    /**
     * bikeMasterJsonString을 BikeStationMasterDto로 파싱
     * @param bikeMasterJsonString
     * @return BikeStationMasterDto
     */
    private BikeStationMasterDto parseBikeStationMaster(String bikeMasterJsonString) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(bikeMasterJsonString, JsonObject.class);

        JsonObject bikeStationMasterAsJsonObject = jsonObject.get("bikeStationMaster").getAsJsonObject();
        int listTotalCount = bikeStationMasterAsJsonObject.get("list_total_count").getAsInt();

        JsonObject resultAsJsonObject = bikeStationMasterAsJsonObject.get("RESULT").getAsJsonObject();
        BikeStationMasterDto.Result result = BikeStationMasterDto.Result.builder()
                .code(resultAsJsonObject.get("CODE").getAsString())
                .message(resultAsJsonObject.get("MESSAGE").getAsString())
                .build();

        JsonArray rowAsJsonArray = bikeStationMasterAsJsonObject.get("row").getAsJsonArray();
        List<BikeStationMasterDto.BikeStationMaster> bikeStationMasters = new ArrayList<>();
        for (int i = 0; i < rowAsJsonArray.size(); i++) {
            JsonObject rowAsJsonObject = rowAsJsonArray.get(i).getAsJsonObject();
            bikeStationMasters.add(
                    BikeStationMasterDto.BikeStationMaster.builder()
                            .id(rowAsJsonObject.get("LENDPLACE_ID").getAsString())
                            .address1(rowAsJsonObject.get("STATN_ADDR1").getAsString())
                            .address2(rowAsJsonObject.get("STATN_ADDR2").getAsString())
                            .lat(rowAsJsonObject.get("STATN_LAT").getAsDouble())
                            .lnt(rowAsJsonObject.get("STATN_LNT").getAsDouble())
                            .build());
        }

        return BikeStationMasterDto.builder()
                .listTotalCount(listTotalCount)
                .result(result)
                .bikeStationMasters(bikeStationMasters)
                .build();
    }

    /**
     * Bike Master 정보 조회
     * @param startIndex
     * @param endIndex
     * @return BikeMaster Json String
     */
    private String getBikeMasterJsonString(int startIndex, int endIndex) {
        // TODO index 추가 필요 (최대 데이터 1000건) -> 로직 구현 추가, 테스트를 위한 코드
//        String apiUrl = getUrl(1, 1);
        String apiUrl = getUrl(startIndex, endIndex);

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            // ResponseCode = 200, 201, 400, 404 ...
            int responseCode = httpURLConnection.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();

        } catch (Exception e) {
            // Exception 다루기
            e.printStackTrace();
            return "failed to get response";
        }
    }

    private String getUrl(int startIndex, int endIndex) {
        String separator = "/";
        return baseUrl + key + separator + type + separator + service + separator + startIndex + separator + endIndex;
    }
}
