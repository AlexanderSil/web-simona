package com.simona.service;

import com.simona.model.Rservice;
import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.RserviceDTO;
import com.simona.model.dto.StationDTO;

import java.util.List;

public interface DaoService {

    List<StationDTO> getStationDTOs();

    List<PostDTO> getPostDTOs();

    List<RegionDTO> getRegionDTOs();

    List<RserviceDTO> getRserviceDTOs();
}
