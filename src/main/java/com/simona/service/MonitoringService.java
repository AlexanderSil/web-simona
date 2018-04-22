package com.simona.service;

import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;

import java.util.List;

public interface MonitoringService {

    List<RegionDTO> getRegionsDTO();

    List<PointDTO> getAggregatedPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom, List<String> regions, List<String> posts);
}
