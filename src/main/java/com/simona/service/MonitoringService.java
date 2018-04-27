package com.simona.service;

import com.simona.model.MonitoringObjects;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.UpdatePointDTO;

import java.util.List;

public interface MonitoringService {

    List<RegionDTO> getRegionsDTO();

    List<PointDTO> getAggregatedPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom);

    void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID);

    void updatePostLocation(Double coordLat, Double coordLon, Integer speed, Double direction, Integer postID, String type, Integer packetID);

//    MonitoringObjects getMonitoringObjects();
}
