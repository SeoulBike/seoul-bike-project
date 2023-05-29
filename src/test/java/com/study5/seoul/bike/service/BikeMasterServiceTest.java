package com.study5.seoul.bike.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.study5.seoul.bike.repository.BikeMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BikeMasterServiceTest {

    @Mock
    private BikeMasterRepository bikeMasterRepository;

    @InjectMocks
    private BikeMasterService bikeMasterService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(bikeMasterService, "baseUrl", "http://openapi.seoul.go.kr:8088/");
        ReflectionTestUtils.setField(bikeMasterService, "key", "706f46794e6a696e373761666e6771");
        ReflectionTestUtils.setField(bikeMasterService, "type", "json");
        ReflectionTestUtils.setField(bikeMasterService, "service", "bikeStationMaster");
    }

    @Test
    @DisplayName("바이크 마스터 정보 가져오기 - 성공")
    void getBikeMasterString() {
        // given
        String bikeMasterString = bikeMasterService.getBikeMasterString(1, 1);

        // when
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(bikeMasterString, JsonObject.class);
        JsonElement resultElement = jsonObject.get("bikeStationMaster").getAsJsonObject().get("RESULT");

        String code = resultElement.getAsJsonObject().get("CODE").getAsString();
        String message = resultElement.getAsJsonObject().get("MESSAGE").getAsString();

        // then
        assertEquals(code, "INFO-000");
        assertEquals(message, "정상 처리되었습니다");
    }

}