package com.simona.service;

import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.RserviceDTO;
import com.simona.model.dto.StationDTO;

import java.util.List;
import java.util.Set;

public interface DaoService {

    Boolean getMarker();

    Set<StationDTO> getStationDTOs();

    List<PostDTO> getPostDTOs();

    List<RegionDTO> getRegionDTOs();

    void setRegionDTOs(List<RegionDTO> regionDTOs);

    List<RserviceDTO> getRserviceDTOs();
}
