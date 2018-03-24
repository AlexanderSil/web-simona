package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.MobileRadioMonitoringStationDto;
import com.simona.model.dto.RegionDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
@Service
public class DtoServiceImpl implements DtoService {
    @Override
    public List<RegionDto> getRegionDtos(List<Region> regionList) {
        List<RegionDto> regionDtos = new LinkedList<>();
        if (regionList != null && !regionList.isEmpty()) {
            for (Region region : regionList) {
                RegionDto regionDto = new RegionDto();

                regionDto.setId(region.getId());
                regionDto.setLatitudeX(region.getLatitudeX());
                regionDto.setLatitudeY(region.getLatitudeY());
                regionDto.setLongitudeX(region.getLongitudeX());
                regionDto.setLongitudeY(region.getLongitudeY());
                regionDto.setMobileRadioMonitoringStations(getMobileRadioMonitoringStationsDtos(region.getMobileRadioMonitoringStations()));

                regionDtos.add(regionDto);
            }
        }
        return regionDtos;
    }

    @Override
    public List<MobileRadioMonitoringStationDto> getMobileRadioMonitoringStationsDtos(List<MobileRadioMonitoringStation> mobileRadioMonitoringStations) {
        List<MobileRadioMonitoringStationDto> mobileRadioMonitoringStationDtos = new LinkedList<>();
        if (mobileRadioMonitoringStations != null && !mobileRadioMonitoringStations.isEmpty()) {
            for (MobileRadioMonitoringStation mobileRadioMonitoringStation: mobileRadioMonitoringStations) {
                MobileRadioMonitoringStationDto mobileRadioMonitoringStationDto= new MobileRadioMonitoringStationDto();

                mobileRadioMonitoringStationDto.setId(mobileRadioMonitoringStation.getId());
                mobileRadioMonitoringStationDto.setLatitude(mobileRadioMonitoringStation.getLatitude());
                mobileRadioMonitoringStationDto.setLongitude(mobileRadioMonitoringStation.getLongitude());
                mobileRadioMonitoringStationDto.setType(mobileRadioMonitoringStation.getType());
                mobileRadioMonitoringStationDto.setBaseStations(getBaseStationsDtos(mobileRadioMonitoringStation.getBaseStations()));

                mobileRadioMonitoringStationDtos.add(mobileRadioMonitoringStationDto);
            }
        }
        return mobileRadioMonitoringStationDtos;
    }

    @Override
    public List<BaseStationDto> getBaseStationsDtos(List<BaseStation> baseStations) {
        List<BaseStationDto> baseStationDtos = new LinkedList<>();
        if (baseStations != null && !baseStations.isEmpty()) {
            for (BaseStation baseStation : baseStations) {
                BaseStationDto baseStationDto = new BaseStationDto();

                baseStationDto.setId(baseStation.getId());
                baseStationDto.setLongitude(baseStation.getLongitude());
                baseStationDto.setLatitude(baseStation.getLatitude());
                baseStationDto.setType(baseStation.getType());

                baseStationDtos.add(baseStationDto);
            }
        }
        return baseStationDtos;
    }
}
