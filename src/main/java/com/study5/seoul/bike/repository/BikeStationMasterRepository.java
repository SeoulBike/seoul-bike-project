package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.BikeStationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeStationMasterRepository extends JpaRepository<BikeStationMaster, String> {
}
