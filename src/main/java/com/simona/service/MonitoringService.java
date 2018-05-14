package com.simona.service;

import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.UpdatePointDTO;

import java.util.List;

public interface MonitoringService {

    List<RegionDTO> getRegionsDTO();

    List<PointDTO> getAggregatedControlPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom);

    List<PointDTO> getPostsDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom);

    void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID);

    void updatePostLocation(Double coordLat, Double coordLon, Integer speed, Double direction, Integer postID, String type, Integer packetID);

    void updatePostStatus(Integer postID, String status);
}
