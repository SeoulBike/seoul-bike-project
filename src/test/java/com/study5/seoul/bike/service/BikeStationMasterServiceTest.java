package com.study5.seoul.bike.service;

import com.study5.seoul.bike.repository.BikeStationMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BikeStationMasterServiceTest {

    @Mock
    private BikeStationMasterRepository bikeStationMasterRepository;

    @InjectMocks
    private BikeStationMasterService bikeStationMasterService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(bikeStationMasterService, "baseUrl", "http://openapi.seoul.go.kr:8088/");
        ReflectionTestUtils.setField(bikeStationMasterService, "key", "706f46794e6a696e373761666e6771");
        ReflectionTestUtils.setField(bikeStationMasterService, "type", "json");
        ReflectionTestUtils.setField(bikeStationMasterService, "service", "bikeStationMaster");
    }

//    @Test
//    @DisplayName("바이크 마스터 정보 가져오기 - 성공")
//    void getBikeMasterString() {
//        // given
//        String bikeMasterString = bikeMasterService.getBikeMasterString(1, 1);
//
//        // when
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(bikeMasterString, JsonObject.class);
//        JsonElement resultElement = jsonObject.get("bikeStationMaster").getAsJsonObject().get("RESULT");
//
//        String code = resultElement.getAsJsonObject().get("CODE").getAsString();
//        String message = resultElement.getAsJsonObject().get("MESSAGE").getAsString();
//
//        // then
//        assertEquals(code, "INFO-000");
//        assertEquals(message, "정상 처리되었습니다");
//    }

}