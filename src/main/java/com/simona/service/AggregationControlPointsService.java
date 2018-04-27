package com.simona.service;

import com.simona.model.ControlPoint;
import com.simona.model.dto.PointDTO;

import java.util.List;

public interface AggregationControlPointsService {

    List<PointDTO> aggregateControlPoints (Double rightTopLatitude, Double rightTopLongitude,
                                           Double leftBottomLatitude, Double leftBottomLongitude,
                                           Iterable<ControlPoint> controlPointList, Integer zoom);

}
