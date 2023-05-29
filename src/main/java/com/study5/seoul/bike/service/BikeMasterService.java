package com.study5.seoul.bike.service;

import com.study5.seoul.bike.repository.BikeMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class BikeMasterService {

    @Value("${api.url}")
    private String baseUrl;

    @Value("${api.bikeMaster.key}")
    private String key;

    @Value("${api.bikeMaster.type}")
    private String type;

    @Value("${api.bikeMaster.service}")
    private String service;

    private final String separator = "/";


    private final BikeMasterRepository bikeMasterRepository;

    /**
     * Bike Master 정보 조회
     * @return BikeMaster Json String
     */
    public String getBikeMasterString(int startIndex, int endIndex) {
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
        }
        return "";
    }

    private String getUrl(int startIndex, int endIndex) {
        return baseUrl + key + separator + type + separator + service + separator + startIndex + separator + endIndex;
    }
}
