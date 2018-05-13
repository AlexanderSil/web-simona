package com.simona.service;

import com.simona.model.Station;
import com.simona.model.dto.PointDTO;

import java.util.List;

/**
 * Created by alex on 4/1/18.
 */
public interface AggregationStationsService {

    List<PointDTO> aggregateStations (Double rightTopLatitude, Double rightTopLongitude,
                                      Double leftBottomLatitude, Double leftBottomLongitude,
                                      Iterable<Station> stations, Integer zoom);
}
