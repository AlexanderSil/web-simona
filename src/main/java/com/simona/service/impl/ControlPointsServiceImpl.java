package com.simona.service.impl;

import com.simona.dao.StationDao;
import com.simona.model.Station;
import com.simona.model.dto.*;
import com.simona.service.ControlPointsService;
import com.simona.service.DaoService;
import com.simona.service.DtoService;
import com.simona.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ControlPointsServiceImpl implements ControlPointsService {


    @Autowired
    private AggregationStationsServiceImpl aggregationStationsService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DaoService daoService;


    @Override
    public List<PointDTO> getAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                       Double rightTopLatitude, Double rightTopLongitude,
                                                       Double leftBottomLatitude, Double leftBottomLongitude) {

        Set<StationDTO> stationDTOs = daoService.getStationDTOs();

        List<PointDTO> controlPointDTOs = new LinkedList<>();
        controlPointDTOs.addAll(aggregationStationsService.aggregateStations(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, stationDTOs, zoom));

        return controlPointDTOs;
    }

    @Override
    public List<PointDTO> getActualAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                             Double rightTopLatitude, Double rightTopLongitude,
                                                             Double leftBottomLatitude, Double leftBottomLongitude) {
        List<PointDTO> controlPointDTOs = new LinkedList<>();
        controlPointDTOs.addAll(aggregationStationsService.aggregateStations(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, daoService.getStationDTOs(), zoom));

        return controlPointDTOs;
    }

    @Override
    public void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID) {
        for (StationDTO stationDTO : daoService.getStationDTOs()) {
            for (ControlPointDTO controlPointDTO : stationDTO.getControlPoints()) {
                if (controlPointDTO.getId().equals(point.getPointID())) {

                    stationDTO.setUpdated(LocalDateTime.now());

                    menuService.updatePostInfo(point, controlPointDTO, stationDTO.getRservice());

                    if ("NOTHING".equals(point.getStatus())) {
                        controlPointDTO.setStatus(0);
                    }
                    if ("DETECT".equals(point.getStatus())) {
                        controlPointDTO.setStatus(1);
                    }
                    if ("MEASUREMENT".equals(point.getStatus())){
                        controlPointDTO.setStatus(2);
                    }
                    if ("NOTHING".equals(stationDTO.getStatus())) {
                        stationDTO.setStatus(0);
                    }
                    if ("DETECT".equals(stationDTO.getStatus())) {
                        stationDTO.setStatus(1);
                    }
                    if ("MEASUREMENT".equals(stationDTO.getStatus())){
                        stationDTO.setStatus(2);
                    }
                }
            }
        }
    }

    @Override
    public void updateControlPoint(List<Integer> pointsId, Integer postID, String type, Integer packetID) {
        for (StationDTO stationDTO : daoService.getStationDTOs()) {
            for (ControlPointDTO controlPointDTO : stationDTO.getControlPoints()) {
                for (Integer point : pointsId) {
                    if (controlPointDTO.getId().equals(point)) {
                        stationDTO.setUpdated(LocalDateTime.now());

                    }
                }
            }
        }
    }

}
