package com.simona.dao;

import com.simona.model.MobileRadioMonitoringStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@Repository
public interface MobileRadioMonitoringStationDao  extends JpaRepository<MobileRadioMonitoringStation, Long> {
}
