package com.simona.controller;

import com.simona.model.BaseStation;
import com.simona.model.Region;
import com.simona.model.dto.BaseStationDto;
import com.simona.model.dto.RegionDto;
import com.simona.service.DtoService;
import com.simona.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@RestController
@RequestMapping(value = "/api")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private DtoService dtoService;

    @GetMapping("/regions")
    public List<RegionDto> getRegions() {
        List<Region> regions = monitoringService.getRegions();
        return dtoService.getRegionDtos(regions);
    }

    @GetMapping("/base")
    public List<BaseStationDto> getDisplayedBaseStation(@RequestParam(value = "region",required=false) String region,
                                                        @RequestParam(value = "monitoringStations",required=false) List<String> monitoringStations,
                                                        @RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                        @RequestParam(value = "rightTopLongtitude",required=false ) Double rightTopLongtitude,
                                                        @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                        @RequestParam(value = "leftBottomLongtitude", required=false) Double leftBottomLongtitude,
                                                        @RequestParam(value = "zoom", required=false) Integer zoom,
                                                        @RequestParam(value = "regionIds", required=false) List<Long> regionIds,
                                                        @RequestParam(value = "mrmsIds", required=false) List<String> mrmsNames) {
        return monitoringService.getAggregatedBaseStation(
                rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsNames);
    }
}
