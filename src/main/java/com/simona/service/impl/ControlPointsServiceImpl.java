package com.simona.service.impl;

import com.simona.dao.StationDao;
import com.simona.model.Station;
import com.simona.model.dto.*;
import com.simona.service.ControlPointsService;
import com.simona.service.DtoService;
import com.simona.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ControlPointsServiceImpl implements ControlPointsService {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private AggregationStationsServiceImpl aggregationStationsService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DtoService dtoService;

    private List<StationDTO> stationDTOs = new LinkedList<>();

    @Override
    public List<PointDTO> getAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                       Double rightTopLatitude, Double rightTopLongitude,
                                                       Double leftBottomLatitude, Double leftBottomLongitude) {

        Iterable<Station> stations = stationDao.findAll();//todo select by mrmsNames

        stationDTOs = dtoService.getStationDTOs(stations);

        List<PointDTO> controlPointDTOs = new LinkedList<>();
        controlPointDTOs.addAll(aggregationStationsService.aggregateStations(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, stationDTOs, zoom));

        return controlPointDTOs;
    }

    @Override
    public void clearListPointDTO() {
        stationDTOs = new LinkedList<>();
    }

    @Override
    public List<PointDTO> getActualAggregatedControlPointDTO(Integer zoom, List<Integer> mrmsNames,
                                                             Double rightTopLatitude, Double rightTopLongitude,
                                                             Double leftBottomLatitude, Double leftBottomLongitude) {
        List<PointDTO> controlPointDTOs = new LinkedList<>();
        controlPointDTOs.addAll(aggregationStationsService.aggregateStations(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, stationDTOs, zoom));

        return controlPointDTOs;
    }

    @Override
    public void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID) {
        for (StationDTO stationDTO : stationDTOs) {
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

    public List<StationDTO> getStationDTOs() {
        return stationDTOs;
    }
}
