package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.BikeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeMasterRepository extends JpaRepository<BikeMaster, String> {
}
