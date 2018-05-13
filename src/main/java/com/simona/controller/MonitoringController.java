package com.simona.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.UpdatePointDTO;
import com.simona.service.MonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
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
        return monitoringService.getAggregatedControlPointDTO(
                rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom);
    }

    @GetMapping("/posts")
    public List<PointDTO> getDisplayedPosts(@RequestParam(value = "region",required=false) String region,
                                                  @RequestParam(value = "monitoringStations",required=false) List<String> monitoringStations,
                                                  @RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                  @RequestParam(value = "rightTopLongtitude",required=false ) Double rightTopLongtitude,
                                                  @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                  @RequestParam(value = "leftBottomLongtitude", required=false) Double leftBottomLongtitude,
                                                  @RequestParam(value = "zoom", required=false) Integer zoom,
                                                  @RequestParam(value = "regionIds", required=false) List<String> regionIds,
                                                  @RequestParam(value = "mrmsIds", required=false) List<String> mrmsNames) {
        return monitoringService.getPostsDTO(
                rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom);
    }

//    @MessageMapping("/monitoring")
//    @SendTo("/topic/monitoring")
//    public MonitoringObjects getMonitoringObjects() {
//        return monitoringService.getMonitoringObjects();
//    }
}
