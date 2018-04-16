package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.MobileRadioMonitoringStationDto;
import com.simona.model.dto.RegionDto;

import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
public interface DtoService {
    List<RegionDto> getRegionDtos(List<Region> regionList);

    List<MobileRadioMonitoringStationDto> getMobileRadioMonitoringStationsDtos(List<MobileRadioMonitoringStation> mobileRadioMonitoringStations);

    List<BaseStationDto> getBaseStationsDtos(List<BaseStation> baseStations);

    BaseStationDto getBaseStationDto(BaseStation baseStation);

}
