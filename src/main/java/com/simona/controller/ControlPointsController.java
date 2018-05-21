package com.simona.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.UpdatePointDTO;
import com.simona.service.ControlPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
public class ControlPointsController {

    @Autowired
    private ControlPointsService controlPointsService;

    @GetMapping("/base/stations")
    public List<PointDTO> getBaseStation(@RequestParam(value = "zoom", required=false) Integer zoom,
                                                  @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames,
                                                  @RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                  @RequestParam(value = "rightTopLongtitude",required=false ) Double rightTopLongtitude,
                                                  @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                  @RequestParam(value = "leftBottomLongtitude", required=false) Double leftBottomLongtitude) {
        if (mrmsNames != null) {
            return controlPointsService.getAggregatedControlPointDTO(zoom, mrmsNames,
                    rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude);
        } else {
//            controlPointsService.clearListPointDTO();
            return new LinkedList<>();
        }
    }

    @GetMapping("/base/stations/actual")
    public List<PointDTO> getActualBaseStation(@RequestParam(value = "zoom", required=false) Integer zoom,
                                                  @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames,
                                                  @RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                  @RequestParam(value = "rightTopLongtitude",required=false ) Double rightTopLongtitude,
                                                  @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                  @RequestParam(value = "leftBottomLongtitude", required=false) Double leftBottomLongtitude
                                                  ) {
        if (mrmsNames != null) {
            return controlPointsService.getActualAggregatedControlPointDTO(zoom, mrmsNames,
                    rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude);
        } else {
            return new LinkedList<>();
        }
    }

    @GetMapping("/updatePostControlPointStatus")
    public List<PointDTO> updatePostControlPointStatus(@RequestParam(value = "rightTopLatitude",required=false) Double rightTopLatitude,
                                                       @RequestParam(value = "rightTopLongitude",required=false ) Double rightTopLongitude,
                                                       @RequestParam(value = "leftBottomLatitude", required=false) Double leftBottomLatitude,
                                                       @RequestParam(value = "leftBottomLongitude", required=false) Double leftBottomLongitude,
                                                       @RequestParam(value = "zoom", required=false) Integer zoom,
                                                       @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames,
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

        controlPointsService.updateControlPoint(point, postID, type, packetID);

        if (mrmsNames != null) {
            return controlPointsService.getActualAggregatedControlPointDTO(zoom, mrmsNames,
                    rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude);
        } else {
            return new LinkedList<>();
        }
    }

}
