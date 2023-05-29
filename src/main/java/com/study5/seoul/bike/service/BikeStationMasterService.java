package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    private BikeStationMasterDto parseBikeMaster(String jsonString) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        int listTotalCount = Integer.parseInt(jsonObject.get("list_total_count").getAsString());;

        JsonElement resultElement = jsonObject.get("RESULT");
        BikeStationMasterDto.Result result = BikeStationMasterDto.Result.builder()
                .code(resultElement.getAsJsonObject().get("CODE").getAsString())
                .message(resultElement.getAsJsonObject().get("MESSAGE").getAsString())
                .build();

        JsonArray rowArray = jsonObject.get("row").getAsJsonArray();
        List<BikeStationMasterDto.BikeStationMaster> bikeStationMasters = new ArrayList<>();
        for (int i = 0; i < rowArray.size(); i++) {
            bikeStationMasters.add(
                    BikeStationMasterDto.BikeStationMaster.builder()
                            .id(rowArray.get(i).getAsJsonObject().get("LENDPLACE_ID").getAsString())
                            .address1(rowArray.get(i).getAsJsonObject().get("STATN_ADDR1").getAsString())
                            .address2(rowArray.get(i).getAsJsonObject().get("STATN_ADDR2").getAsString())
                            .lat(rowArray.get(i).getAsJsonObject().get("STATN_LAT").getAsDouble())
                            .lnt(rowArray.get(i).getAsJsonObject().get("STATN_LNT").getAsDouble())
                            .build());
        }

        BikeStationMasterDto bikeStationMasterDto = BikeStationMasterDto.builder()
                .listTotalCount(listTotalCount)
                .result(result)
                .bikeStationMasters(bikeStationMasters)
                .build();

        return bikeStationMasterDto;
    }

    /**
     * Bike Master 정보 조회
     * @return BikeMaster Json String
     */
    private String getBikeMasterString(int startIndex, int endIndex) {
        // TODO index 추가 필요 (최대 데이터 1000건) -> 로직 구현 추가, 테스트를 위한 코드
        String apiUrl = getUrl(1, 1);
//        String apiUrl = getUrl(startIndex, endIndex);

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            // ResponseCode = 200, 201, 400, 400 ...
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
