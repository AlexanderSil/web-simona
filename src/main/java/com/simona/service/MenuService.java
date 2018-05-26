package com.simona.service;

import com.simona.model.dto.*;

import java.util.List;

public interface MenuService {

    List<RegionDTO> getRegionsDTO();

    void updatePostStatus(Integer postID, String status);

    List<RegionDTO> getActualRegions();

    void updatePostInfo(UpdatePointDTO newPoint, ControlPointDTO oldControlPointDTO, StationRserviceDTO rservice);

    void updatePostInfoUnidentifiedCount(List<UnidentifiedStationDTO> unidentifiedStationDTO);
}
