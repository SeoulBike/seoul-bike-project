package com.study5.seoul.bike.scheduler;

import com.study5.seoul.bike.domain.CycleStation;
import com.study5.seoul.bike.dto.CycleStationDto;
import com.study5.seoul.bike.service.CycleStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleStationScheduler {

    private static final int MAX_DATA_AMOUNT = 1000;

    private final CycleStationService cycleStationService;

    @Transactional
//    @Scheduled(cron = "${scheduler.bikeStationMaster.test.cron}")
    @Scheduled(cron = "${scheduler.cycleStation.cron}")
    public void CycleStationScheduling() {
        int listTotalCount = cycleStationService.getListTotalCount();
        int divisor = (listTotalCount % MAX_DATA_AMOUNT == 0) ?
                listTotalCount / MAX_DATA_AMOUNT : (listTotalCount / MAX_DATA_AMOUNT) + 1;

        int startIndex = 1;
        int endIndex = MAX_DATA_AMOUNT;

        for (int i = 0; i < divisor; i++) {

            String cycleStationJsonString = cycleStationService.getCycleStationJsonString(startIndex, endIndex);
            CycleStationDto cycleStationDto = cycleStationService.parseCycleStation(cycleStationJsonString);
            List<CycleStationDto.RowCycleStation> bikeStationDtos = cycleStationDto.getCycleStationDtos();

            List<CycleStation> cycleStations = cycleStationDto.ListDtoToListEntity(bikeStationDtos);
            cycleStationService.saveBikeStations(cycleStations);

            startIndex += MAX_DATA_AMOUNT;
            endIndex += MAX_DATA_AMOUNT;

            /**
             * 스케쥴링 -> 대부분 모듈로 작업
             * TODO 로그 작업 필요 => Table 생성 or 파일 생성
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
}
