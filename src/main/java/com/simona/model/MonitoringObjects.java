package com.simona.model;

import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitoringObjects {

    private String name;

    private List<RegionDTO> regionDTOs;

    private List<PointDTO> pointDTOs;

}

