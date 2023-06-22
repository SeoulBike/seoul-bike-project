package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.CycleStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleStationRepository extends JpaRepository<CycleStation, Long> {
}
