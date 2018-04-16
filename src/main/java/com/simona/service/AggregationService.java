package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.dto.BaseStationDto;

import java.util.List;

/**
 * Created by alex on 4/1/18.
 */
public interface AggregationService {

    List<BaseStationDto> aggregate (List<BaseStation> baseStationList, Integer zoom, Double rightTopLatitude, Double rightTopLongtitude,
                                    Double leftBottomLatitude, Double leftBottomLongtitude);

}
