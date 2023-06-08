package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.study5.seoul.bike.domain.BikeStation;
import com.study5.seoul.bike.dto.BikeStationMasterDto;
import com.study5.seoul.bike.repository.BikeStationMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    private static final int MAX_DATA_AMOUNT = 1000;

    private final BikeStationMasterRepository bikeStationMasterRepository;

    /**
     * TODO service -> scheduler 패키지로 변경(refactor)
     */
    @Transactional
    @Scheduled(cron = "${scheduler.bikeStationMaster.test.cron}")
    public void BikeStationMasterScheduling() {
        int listTotalCount = getListTotalCount();
        int divisor = (listTotalCount % MAX_DATA_AMOUNT == 0) ?
                listTotalCount / MAX_DATA_AMOUNT : (listTotalCount / MAX_DATA_AMOUNT) + 1;

        int startIndex = 1;
//        int endIndex = listTotalCount > MAX_DATA_AMOUNT ? startIndex + 999 : listTotalCount;
        int endIndex = MAX_DATA_AMOUNT;

        for (int i = 0; i < divisor; i++) {

            String bikeStationMasterJsonString = getBikeStationMasterJsonString(startIndex, endIndex);
            BikeStationMasterDto bikeStationMasterDto = parseBikeStationMaster(bikeStationMasterJsonString);
            List<BikeStationMasterDto.BikeStationDto> bikeStationDtos = bikeStationMasterDto.getBikeStationDtos();

            List<BikeStation> bikeStations = bikeStationMasterDto.ListDtoToListEntity(bikeStationDtos);
            saveBikeStations(bikeStations);

            startIndex += MAX_DATA_AMOUNT;
//            endIndex = (divisor != i) ? endIndex + MAX_DATA_AMOUNT : listTotalCount;
            endIndex = endIndex + MAX_DATA_AMOUNT;

            /**
             * TODO 23.06.08 질문하기
             *
             * 1. BikeStation 정보를 저장 중 InterruptedException이 발생하여 break를 탈 경우,
             *    @Transaction rollback이 되나요..?
             *
             * 2. Scheduler를 테스트코드로 작성할 수 있는 방법이 있나요?
             *    현업에서는 스케쥴러는 개발 서버에서 테스트 이후 문제가 없다고 생각된다면 배포하나요?
             */

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * 서울시 따릉이대여소 리스트 저장
     */
    private void saveBikeStations(List<BikeStation> bikeStations) {
        // 시간 복잡도 -> for save() > saveAll(), saveAll()이 더 효율적
        bikeStationMasterRepository.saveAll(bikeStations);
    }

    /**
     * 서울시 따릉이대여소 정보의 개수 조회
     */
    private int getListTotalCount() {
        String bikeMasterJsonString = getBikeStationMasterJsonString(1, 1);
        return parseBikeStationMaster(bikeMasterJsonString).getListTotalCount();
    }

    /**
     * bikeMasterJsonString을 BikeStationMasterDto로 파싱
     * TODO FIX => ERROR 가 나올 경우 Object에서 null이 나온다. + 하드 코딩 처리 어떻게? 그대로?
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
        List<BikeStationMasterDto.BikeStationDto> bikeStationDtos = new ArrayList<>();
        for (int i = 0; i < rowAsJsonArray.size(); i++) {
            JsonObject rowAsJsonObject = rowAsJsonArray.get(i).getAsJsonObject();
            bikeStationDtos.add(
                    BikeStationMasterDto.BikeStationDto.builder()
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
                .bikeStationDtos(bikeStationDtos)
                .build();
    }

    /**
     * 서울시 따릉이대여소 마스터 정보 조회
     */
    public String getBikeStationMasterJsonString(int startIndex, int endIndex) {
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

    /**
     * 서울시 따릉이대여소 마스터 정보 URL 조회
     */
    private String getUrl(int startIndex, int endIndex) {
        String separator = "/";
        return baseUrl + key + separator + type + separator + service + separator + startIndex + separator + endIndex;
    }
}
