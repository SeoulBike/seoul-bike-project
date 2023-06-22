package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.study5.seoul.bike.domain.CycleStation;
import com.study5.seoul.bike.dto.CycleStationDto;
import com.study5.seoul.bike.repository.CycleStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CycleStationService {

    @Value("${api.url}")
    private String baseUrl;

    @Value("${api.CycleStationInfo.key}")
    private String key;

    @Value("${api.CycleStationInfo.type}")
    private String type;

    @Value("${api.CycleStationInfo.service}")
    private String service;

    private final CycleStationRepository cycleStationRepository;

    /**
     * 공공자전거 대여소 리스트 DB 저장
     */
    public void saveBikeStations(List<CycleStation> cycleStations) {
        // 시간 복잡도 -> for save() > saveAll(), saveAll()이 더 효율적
        cycleStationRepository.saveAll(cycleStations);
    }

    /**
     * 공공자전거 대여소 정보의 개수 조회
     */
    public int getListTotalCount() {
        String CycleStationJsonString = getCycleStationJsonString(1, 1);
        return parseCycleStation(CycleStationJsonString).getListTotalCount();
    }

    /**
     * 파싱(JsonString -> CycleStationDto)
     */
    public CycleStationDto parseCycleStation(String CycleStationJsonString) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(CycleStationJsonString, JsonObject.class);

        JsonObject stationInfoAsJsonObject = jsonObject.get("stationInfo").getAsJsonObject();

        // TODO 로직 고민 -> null 일 경우 CustomException(code, message)
        if (null == stationInfoAsJsonObject) {
            throw new RuntimeException("데이터 요청에 실패했습니다.");
        }

        int listTotalCount = stationInfoAsJsonObject.get("list_total_count").getAsInt();

        JsonObject resultAsJsonObject = stationInfoAsJsonObject.get("RESULT").getAsJsonObject();
        CycleStationDto.Result result = CycleStationDto.Result.builder()
                .code(resultAsJsonObject.get("CODE").getAsString())
                .message(resultAsJsonObject.get("MESSAGE").getAsString())
                .build();

        JsonArray rowAsJsonArray = stationInfoAsJsonObject.get("row").getAsJsonArray();
        List<CycleStationDto.RowCycleStation> rowCycleStations = new ArrayList<>();
        for (int i = 0; i < rowAsJsonArray.size(); i++) {
            JsonObject rowAsJsonObject = rowAsJsonArray.get(i).getAsJsonObject();
            rowCycleStations.add(
                    CycleStationDto.RowCycleStation.builder()
                            .district(rowAsJsonObject.get("STA_LOC").getAsString())
                            .rentId(rowAsJsonObject.get("RENT_ID").getAsString())
                            .rentNm(rowAsJsonObject.get("RENT_NM").getAsString())
                            .rentNo(rowAsJsonObject.get("RENT_NO").getAsString())
                            .rentIdNm(rowAsJsonObject.get("RENT_ID_NM").getAsString())
                            // TODO HOLD_NUM 필드가 아에 안내려오는 값 존재
//                            .holdNum(Integer.valueOf(rowAsJsonObject.get("HOLD_NUM").getAsString()))
                            .address1(rowAsJsonObject.get("STA_ADD1").getAsString())
                            .address2(rowAsJsonObject.get("STA_ADD2").getAsString())
                            .lat(Double.parseDouble(rowAsJsonObject.get("STA_LAT").getAsString()))
                            .lnt(Double.parseDouble(rowAsJsonObject.get("STA_LONG").getAsString()))
                            .build());
        }

        return CycleStationDto.builder()
                .listTotalCount(listTotalCount)
                .result(result)
                .cycleStationDtos(rowCycleStations)
                .build();
    }

    /**
     * 공공자전거 대여소 정보 조회
     */
    public String getCycleStationJsonString(int startIndex, int endIndex) {
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
     * 공공자전거 대여소 정보 조회
     */
    private String getUrl(int startIndex, int endIndex) {
        String separator = "/";
        return baseUrl + key + separator + type + separator + service + separator + startIndex + separator + endIndex;
    }
}
