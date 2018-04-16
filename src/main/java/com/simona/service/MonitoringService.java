package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.RegionDto;

import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
public interface MonitoringService {

    List<Region> getRegions();

    List<BaseStationDto> getAggregatedBaseStation(Double rightTopLatitude, Double rightTopLongtitude,
                                     Double leftBottomLatitude, Double leftBottomLongtitude, Integer zoom, List<Long> regionIds, List<String> mrmsNames);

    List<BaseStation> getRandomBaseStation(Double rightTopLatitude, Double rightTopLongtitude,
                                     Double leftBottomLatitude, Double leftBottomLongtitude, Integer zoom);

}
