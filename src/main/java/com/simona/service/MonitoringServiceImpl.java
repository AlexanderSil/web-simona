package com.simona.service;

import com.simona.dao.BaseStationDao;
import com.simona.dao.DaoMock;
import com.simona.dao.MobileRadioMonitoringStationDao;
import com.simona.dao.RegionDao;
import com.simona.model.BaseStation;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.RegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by alex on 3/19/18.
 */
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    private DaoMock daoMock;

    @Autowired
    private DtoService dtoService;

//    @Autowired
//    private RegionDao regionDao;

//    @Autowired
//    private MobileRadioMonitoringStationDao mobileRadioMonitoringStationDao;

//    @Autowired
//    private BaseStationDao baseStationDao;

    @Autowired
    private AggregationService aggregationService;

    @Override
    public List<Region> getRegions() {
        List<Region> regionList = daoMock.findAllRegions();

        List<MobileRadioMonitoringStation> mobileRadioMonitoringStation;
        for (Region region : regionList) {
            mobileRadioMonitoringStation = daoMock.findMobileStationByRegion(region.getId());
            region.setMobileRadioMonitoringStations(mobileRadioMonitoringStation);
        }

        return regionList;
    }

    protected static Random random = new Random();

    @Override
    public List<BaseStationDto> getAggregatedBaseStation(Double rightTopLatitude, Double rightTopLongtitude,
                                            Double leftBottomLatitude, Double leftBottomLongtitude, Integer zoom,
                                                         List<Long> regionIds, List<String> mrmsNames) {

//        return aggregationService.aggregate(generateRandomBaseStation(500), zoom, rightTopLatitude, rightTopLongtitude,
//                 leftBottomLatitude, leftBottomLongtitude);
        List<BaseStationDto> baseStationDtoList = new LinkedList<>();

        baseStationDtoList.addAll(dtoService.getBaseStationsDtos(daoMock.getPointerMRMS(regionIds, mrmsNames)));

        List<BaseStation> baseStationList = daoMock.getBaseStationsByRegionMRMS(regionIds, mrmsNames);

                baseStationDtoList.addAll(aggregationService.aggregate(
                        baseStationList, zoom, rightTopLatitude, rightTopLongtitude,
                leftBottomLatitude, leftBottomLongtitude));
        return baseStationDtoList;

    }

    @Override
    public List<BaseStation> getRandomBaseStation(Double rightTopLatitude, Double rightTopLongtitude,
                                            Double leftBottomLatitude, Double leftBottomLongtitude, Integer zoom) {
        return generateRandomBaseStation(10);
    }

    private List<BaseStation> generateRandomBaseStation(Integer count) {
        List<BaseStation> baseStations = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            BaseStation baseStation = new BaseStation();
            baseStation.setId(Long.valueOf(i));
            baseStation.setIconName(generateType());//'yellow''grey''green'
            baseStation.setLatitude(generateLatitude());//49.988982 - 49.903915
            baseStation.setLongitude(generateLongitude());//36.222230 - 36.090130
            baseStations.add(baseStation);
        }
        return baseStations;
    }

    private String generateType() {
        int x = random.nextInt(3);
        if (x == 0) {
            return "yellow.png";
        }
        if (x == 1) {
            return "grey.png";
        }
        if (x == 2) {
            return "green.png";
        }
        return "green.png";
    }



    private static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    /**
     * max lat - 51.0 ((51 + 90.0) / 180.0 =  0,783333333333333)
     * min lat - 46.0 ((46 + 90.0) / 180.0 = 0,755555555555556)
     * горизонталь
     * (ex.: (49.903915 + 90.0) / 180.0)
     */
    private Double generateLatitude() {// 0.7772439722222221  \\\0.7782309944444444
//        return (randomInRange(0.7772439722222221, 0.7782309944444444) * 180) - 90.0;
        return (randomInRange(0.755555555555556, 0.783333333333333) * 180) - 90.0;
    }

    /**
     * max 40.0 ((40 + 180.0) / 360.0 = 0,611111111111111)
     * min 30.0 ((30 + 180.0) / 360.0 = 0,583333333333333)
     * вертикаль
     * (ex.: (36.090130 + 180.0) / 360.0)
     */
    private Double generateLongitude() {//// 0.6002503611111111 ; 0.6014424527777777 (ex.: (36.090130 + 180.0) / 360.0)
        return (randomInRange(0.583333333333333, 0.611111111111111) * 360) - 180.0;
    }

}
