package com.simona.dao;

import com.simona.model.BaseStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@Repository
public interface BaseStationDao  extends JpaRepository<BaseStation, Long> {

//    List<BaseStation> selectByRange(Double rightTopLatitude, Double rightTopLongtitude,
//                                   Double leftBottomLatitude, Double leftBottomLongtitude);
}
