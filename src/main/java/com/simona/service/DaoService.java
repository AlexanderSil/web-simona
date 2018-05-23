package com.simona.service;

import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.RserviceDTO;
import com.simona.model.dto.StationDTO;

import java.util.List;
import java.util.Set;

public interface DaoService {

    Set<StationDTO> getStationDTOs();

    List<PostDTO> getPostDTOs();

    List<RegionDTO> getRegionDTOs();

    List<RserviceDTO> getRserviceDTOs();
}
