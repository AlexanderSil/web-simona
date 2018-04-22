package com.simona.controller;

import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/regions")
    public List<RegionDTO> getRegions() {
        return monitoringService.getRegionsDTO();
    }

    @GetMapping("/points")
    public List<PointDTO> getDisplayedBaseStation(@RequestParam(value = "region",required=false) String region,
                                                  @RequestParam(value = "monitoringStations",required=false) List<String> monitoringStations,
                                                  @RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                  @RequestParam(value = "rightTopLongtitude",required=false ) Double rightTopLongtitude,
                                                  @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                  @RequestParam(value = "leftBottomLongtitude", required=false) Double leftBottomLongtitude,
                                                  @RequestParam(value = "zoom", required=false) Integer zoom,
                                                  @RequestParam(value = "regionIds", required=false) List<String> regionIds,
                                                  @RequestParam(value = "mrmsIds", required=false) List<String> mrmsNames) {
        return monitoringService.getAggregatedPointDTO(
                rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsNames);
    }
}
