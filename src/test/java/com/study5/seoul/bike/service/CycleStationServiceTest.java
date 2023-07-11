package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.study5.seoul.bike.repository.CycleStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study5.seoul.bike.type.code.ApiErrorCode.INFO_000;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CycleStationServiceTest {

    @Mock
    private CycleStationRepository cycleStationRepository;

    @InjectMocks
    private CycleStationService cycleStationService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(cycleStationService, "baseUrl", "http://openapi.seoul.go.kr:8088/");
        ReflectionTestUtils.setField(cycleStationService, "key", "4265464a716a696e34324d4c646f79");
        ReflectionTestUtils.setField(cycleStationService, "type", "json");
        ReflectionTestUtils.setField(cycleStationService, "service", "tbCycleStationInfo");
    }

    @Test
    @DisplayName("바이크 마스터 정보 가져오기 - 성공")
    void getBikeMasterStringSuccess() {
        // given
        String cycleStationJsonString = cycleStationService.getCycleStationJsonString(1, 1);

        // when
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(cycleStationJsonString, JsonObject.class);
        JsonElement resultElement = jsonObject.get("stationInfo").getAsJsonObject().get("RESULT");

        String resultCode = resultElement.getAsJsonObject().get("CODE").getAsString();

        // then
        assertEquals(resultCode, INFO_000.getCode());
    }

}