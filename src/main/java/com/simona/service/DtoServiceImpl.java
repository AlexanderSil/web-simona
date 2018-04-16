package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.DetectedStationDto;
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
                regionDto.setName(region.getRegionName());

                regionDto.setLatitudeX(region.getLatitudeX());
                regionDto.setLongitudeX(region.getLongitudeX());

                regionDto.setLatitudeY(region.getLatitudeY());
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
                mobileRadioMonitoringStationDto.setName(mobileRadioMonitoringStation.getNameStation());

                mobileRadioMonitoringStationDto.setLatitudeX(mobileRadioMonitoringStation.getLatitudeX());
                mobileRadioMonitoringStationDto.setLongitudeX(mobileRadioMonitoringStation.getLongitudeX());

                mobileRadioMonitoringStationDto.setLatitudeY(mobileRadioMonitoringStation.getLatitudeY());
                mobileRadioMonitoringStationDto.setLongitudeY(mobileRadioMonitoringStation.getLongitudeY());

                mobileRadioMonitoringStationDto.setDetected(getDetectedStationsDtos());

                mobileRadioMonitoringStationDto.setStatus(mobileRadioMonitoringStation.getStatus());
                mobileRadioMonitoringStationDto.setIconName(mobileRadioMonitoringStation.getIconName());

                mobileRadioMonitoringStationDtos.add(mobileRadioMonitoringStationDto);
            }
        }
        return mobileRadioMonitoringStationDtos;
    }

    private List<DetectedStationDto> getDetectedStationsDtos() {//todo hard-cod!!! just for testing.
        List<DetectedStationDto> detectedStationDtos = new LinkedList<>();
        DetectedStationDto detectedStationDto = new DetectedStationDto();
        detectedStationDto.setName("GSM900");
        detectedStationDto.setCount(26);
        detectedStationDtos.add(detectedStationDto);

        detectedStationDto = new DetectedStationDto();
        detectedStationDto.setName("GSM1800");
        detectedStationDto.setCount(423);
        detectedStationDtos.add(detectedStationDto);

        detectedStationDto = new DetectedStationDto();
        detectedStationDto.setName("WCDMA");
        detectedStationDto.setCount(213);
        detectedStationDtos.add(detectedStationDto);

        detectedStationDto = new DetectedStationDto();
        detectedStationDto.setName("Total");
        detectedStationDto.setCount(662);
        detectedStationDtos.add(detectedStationDto);

        return detectedStationDtos;
    }

    @Override
    public List<BaseStationDto> getBaseStationsDtos(List<BaseStation> baseStations) {
        List<BaseStationDto> baseStationDtos = new LinkedList<>();
        if (baseStations != null && !baseStations.isEmpty()) {
            for (BaseStation baseStation : baseStations) {
                BaseStationDto baseStationDto = getBaseStationDto(baseStation);
                baseStationDtos.add(baseStationDto);
            }
        }
        return baseStationDtos;
    }

    public BaseStationDto getBaseStationDto(BaseStation baseStation) {
        BaseStationDto baseStationDto = new BaseStationDto();

        baseStationDto.setLongitude(baseStation.getLongitude());
        baseStationDto.setLatitude(baseStation.getLatitude());
        baseStationDto.setImageName(baseStation.getIconName());
        return baseStationDto;
    }
}
