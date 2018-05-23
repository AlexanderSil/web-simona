package com.simona.service;

import com.simona.model.dto.PointDTO;
import com.simona.model.dto.StationDTO;
import com.simona.model.dto.UpdatePointDTO;

import java.util.List;

/**
 * Created by alex on 5/16/18.
 */
public interface ControlPointsService {

    List<PointDTO> getAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude);

    List<PointDTO> getActualAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude);

    void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID);

    void updateControlPoint(List<Integer> pointsId, Integer postID, String type, Integer packetID);

//    List<StationDTO> getStationDTOs();
}
