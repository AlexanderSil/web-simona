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
        return monitoringService.getAggregatedPointDTO(
                rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom);
    }


    @GetMapping("/updatePostLocation")
    public List<PointDTO> updatePostLocation(@RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                             @RequestParam(value = "rightTopLongitude",required=false ) Double rightTopLongitude,
                                             @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                             @RequestParam(value = "leftBottomLongitude", required=false) Double leftBottomLongitude,
                                             @RequestParam(value = "zoom", required=false) Integer zoom,
                                             @RequestParam(value = "coordLat", required=false) Double coordLat,
                                             @RequestParam(value = "coordLon", required=false) Double coordLon,
                                             @RequestParam(value = "speed", required=false) Integer speed,
                                             @RequestParam(value = "direction", required=false) Double direction,
                                             @RequestParam(value = "postID", required=false) Integer postID,
                                             @RequestParam(value = "type", required=false) String type,
                                             @RequestParam(value = "packetID", required=false) Integer packetID) {
        log.info("Update Post Location| " + "{\"coord\":{\"lat\":" + coordLat + ",\"lon\":" + coordLon + "},\"speed\":" + speed +
                ",\"direction\":" + direction + ",\"postID\":" + postID + ",\"type\":\"" + type + "\",\"packetID\":" + packetID + "}");

        monitoringService.updatePostLocation(coordLat, coordLon, speed, direction, postID, type, packetID);

        if (leftBottomLatitude < coordLat && coordLat < rightTopLatitude
                && leftBottomLongitude < coordLon && coordLon < rightTopLongitude) {
            return monitoringService.getAggregatedPointDTO(
                    rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude, zoom);
        }
        return new ArrayList<>();
    }

    @GetMapping("/updatePostControlPointStatus")
    public List<PointDTO> updatePostControlPointStatus(@RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                       @RequestParam(value = "rightTopLongitude",required=false ) Double rightTopLongitude,
                                                       @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                       @RequestParam(value = "leftBottomLongitude", required=false) Double leftBottomLongitude,
                                                       @RequestParam(value = "zoom", required=false) Integer zoom,
                                                       @RequestParam(value = "points", required=false) String points,
                                                       @RequestParam(value = "postID", required=false) Integer postID,
                                                       @RequestParam(value = "type", required=false) String type,
                                                       @RequestParam(value = "packetID", required=false) Integer packetID) {
        ObjectMapper mapper = new ObjectMapper();
        UpdatePointDTO point = new UpdatePointDTO();
        try {
            point = mapper.readValue(points, UpdatePointDTO.class);
        } catch (IOException e) {
            log.error("Update Post Control Point Status. Can't parse point! ---> " + e.getMessage());
        }
        log.info("Update Post Control Point Status| " + "{\"points\":[{\"postStatus\":\"" + point.getPostStatus() + "\",\"pointID\":" + point.getPointID() +
                ",\"status\":\"" + point.getStatus() + "\"}],\"postID\":" + postID + ",\"type\":\"" + type + "\",\"packetID\":" + packetID + "}");

        monitoringService.updateControlPoint(point, postID, type, packetID);

        return monitoringService.getAggregatedPointDTO(
                rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude, zoom);
    }


    @GetMapping("/updatePostStatus")
    public HttpStatus updatePostStatus(@RequestParam(value = "status",required=false) String status,
                                                  @RequestParam(value = "postID",required=false) Integer postID,
                                                  @RequestParam(value = "type",required=false) String type,
                                                  @RequestParam(value = "packetID",required=false ) Integer packetID) {
        log.info("Update Post Status| " + "{\"status\":\"" + status + "\",\"postID\":" + postID + ",\"type\":\"" + type + "\",\"packetID\":" + packetID + "}");
        return HttpStatus.OK;
    }


//    @MessageMapping("/monitoring")
//    @SendTo("/topic/monitoring")
//    public MonitoringObjects getMonitoringObjects() {
//        return monitoringService.getMonitoringObjects();
//    }
}
