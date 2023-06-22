//package com.study5.seoul.bike.service;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.study5.seoul.bike.repository.BikeStationMasterRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static com.study5.seoul.bike.type.ApiResponseCode.ERROR_336;
//import static com.study5.seoul.bike.type.ApiResponseCode.INFO_000;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class BikeStationMasterServiceTest {
//
//    @Mock
//    private BikeStationMasterRepository bikeStationMasterRepository;
//
//    @InjectMocks
//    private BikeStationMasterService bikeStationMasterService;
//
//    @BeforeEach
//    public void setup() {
//
//        // TODO file 읽어서 작성 < loading 빨리 되는 걸 활용해서 작성(WAS + Spring framework => 공부)
//        ReflectionTestUtils.setField(bikeStationMasterService, "baseUrl", "http://openapi.seoul.go.kr:8088/");
//        ReflectionTestUtils.setField(bikeStationMasterService, "key", "706f46794e6a696e373761666e6771");
//        ReflectionTestUtils.setField(bikeStationMasterService, "type", "json");
//        ReflectionTestUtils.setField(bikeStationMasterService, "service", "bikeStationMaster");
//    }
//
////    @Test
////    @DisplayName("바이크 마스터 정보 파싱 - 성공")
////    void parseBikeStationMaster() {
////        // given
////        int startIndex = 1;
////        int endIndex = 5;
////        String bikeMasterString = bikeStationMasterService.getBikeMasterJsonString(startIndex, endIndex);
////
////        // when
////        BikeStationMasterDto bikeStationMasterDto = bikeStationMasterService.parseBikeStationMaster(bikeMasterString);
////
////        String resultCode = bikeStationMasterDto.getResult().getCode();
////        String resultMessage = bikeStationMasterDto.getResult().getMessage();
////
////        List<BikeStationMasterDto.BikeStationMaster> bikeStationMasters = bikeStationMasterDto.getBikeStationMasters();
////
////        // then
////        assertEquals(resultCode, INFO_000.getCode());
//////        assertEquals(resultMessage, "정상 처리되었습니다");
////        assertEquals(bikeStationMasters.size(), endIndex - startIndex + 1);
////    }
//
//    @Test
//    @DisplayName("바이크 마스터 정보 가져오기 - 성공")
//    void getBikeMasterStringSuccess() {
//        // given
//        String bikeMasterString = bikeStationMasterService.getBikeStationMasterJsonString(1, 1);
//
//        // when
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(bikeMasterString, JsonObject.class);
//        JsonElement resultElement = jsonObject.get("bikeStationMaster").getAsJsonObject().get("RESULT");
//
//        String resultCode = resultElement.getAsJsonObject().get("CODE").getAsString();
//
//        // then
//        assertEquals(resultCode, INFO_000.getCode());
//    }
//
//    @Test
//    @DisplayName("바이크 마스터 정보 가져오기 - 실패(1000개 초과한 데이터 조회)")
//    void getBikeMasterStringFail() {
//        // given
//        String bikeMasterString = bikeStationMasterService.getBikeStationMasterJsonString(1, 1001);
//
//        // when
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(bikeMasterString, JsonObject.class);
////        System.out.println("jsonObject = " + jsonObject);
////        JsonElement resultElement = jsonObject.get("bikeStationMaster").getAsJsonObject().get("RESULT");
//        JsonElement resultElement = jsonObject.get("RESULT");
//
//        String resultCode = resultElement.getAsJsonObject().get("CODE").getAsString();
//
//        // then
//        assertEquals(resultCode, ERROR_336.getCode());
//    }
//
//}