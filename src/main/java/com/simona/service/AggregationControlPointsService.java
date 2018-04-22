package com.simona.service;

import com.simona.model.ControlPoint;
import com.simona.model.dto.PointDTO;

import java.util.List;

public interface AggregationControlPointsService {

    List<PointDTO> aggregateControlPoints (Iterable<ControlPoint> controlPointList, Integer zoom);

}
