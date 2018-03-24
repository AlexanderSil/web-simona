package com.simona.service;

import com.simona.dao.BaseStationDao;
import com.simona.dao.MobileRadioMonitoringStationDao;
import com.simona.dao.RegionDao;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import com.simona.model.dto.RegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private MobileRadioMonitoringStationDao mobileRadioMonitoringStationDao;

    @Autowired
    private BaseStationDao baseStationDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public List<RegionDto> getRegionsDto() {
        Region region = regionDao.getOne(1L);

        MobileRadioMonitoringStation mobileRadioMonitoringStation
                = mobileRadioMonitoringStationDao.getOne(1L);

        mobileRadioMonitoringStation.setBaseStations(baseStationDao.findAll());

        List<MobileRadioMonitoringStation> mobileRadioMonitoringStations = new LinkedList<>();
        mobileRadioMonitoringStations.add(mobileRadioMonitoringStation);
        region.setMobileRadioMonitoringStations(mobileRadioMonitoringStations);
        List<Region> regions = new LinkedList<>();
        regions.add(region);

        return dtoService.getRegionDtos(regions);
    }
}
