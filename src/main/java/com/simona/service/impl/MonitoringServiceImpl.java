package com.simona.service.impl;

import com.simona.dao.*;
import com.simona.model.*;
import com.simona.model.dto.*;
//import com.simona.service.AggregationControlPointsService;
import com.simona.service.DtoService;
import com.simona.service.MonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    private DaoMock daoMock;

    @Autowired
    private DtoService dtoService;

//    @Autowired
//    private AggregationControlPointsService aggregationControlPointsService;

    @Autowired
    private AggregationStationsServiceImpl aggregationStationsService;

    @Autowired
    private ControlPointDao controlPointDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private RserviceDao rserviceDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostTracesDao postTracesDao;

    private Date lastGettingControlPointFromDB;
    public Iterable<ControlPoint> controlPoints;

    private Date lastGettingStationsFromDB;
    private Iterable<Station> stations;

    private Date lastGettingPostTracesFromDB;
    private PostTraces lastPostTraces;

    private Date lastGettingPostsFromDB;
    private Iterable<Post> posts;

    private Date lastGettingRservicesFromDB;
    private Iterable<Rservice> rservices;

    private List<RegionDTO> regionDtos;

    @Override
    public List<RegionDTO> getRegionsDTO() {

        //Add regions
        List<Region> regionList = daoMock.findAllRegions();
        regionDtos = dtoService.getRegionDTOs(regionList);

        if (lastGettingPostsFromDB == null
                || (Math.abs(lastGettingPostsFromDB.getTime() - new Date().getTime())/60)/1000 > 60) { //update data from db 1 hour
            lastGettingPostsFromDB = new Date();
            posts = postDao.findAll();
        }
        if (lastGettingRservicesFromDB == null
                || (Math.abs(lastGettingRservicesFromDB.getTime() - new Date().getTime())/60)/1000 > 60) { //update data from db 1 hour
            lastGettingRservicesFromDB = new Date();
            rservices = rserviceDao.findAll();
        }

        if (lastGettingControlPointFromDB == null
                || (Math.abs(lastGettingControlPointFromDB.getTime() - new Date().getTime())/60)/1000 > 10) { //update data from db 10 min
            lastGettingControlPointFromDB = new Date();
            controlPoints = controlPointDao.findAll();
        }

        //Add Rservices to Posts
        List<PostDTO> postDTOs = new LinkedList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            postDTO = dtoService.getPostDTO(post);
            postDTO.setRserviceDTOs(dtoService.getRserviceDTOs(rservices));
            postDTOs.add(postDTO);
        }
        for (RegionDTO regionDto : regionDtos) {
            regionDto.setPostDTOs(postDTOs);
        }

        return regionDtos;
    }


    public List<PointDTO> getAggregatedControlPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                       Double leftBottomLatitude, Double leftBottomLongitude,
                                                       Integer zoom) {
        List<PointDTO> controlPointDTOs = new LinkedList<>();

//        if (lastGettingControlPointFromDB == null
//                || (Math.abs(lastGettingControlPointFromDB.getTime() - new Date().getTime())/60)/1000 > 10) { //update data from db 10 min
//            lastGettingControlPointFromDB = new Date();

//            Iterable<Integer> controlPointsIds = new ArrayList<>();
//            List<Integer> controlPointsIds = new ArrayList<>();
//            controlPointsIds.add(5931);
//            controlPoints = controlPointDao.findAllById(controlPointsIds);
//            controlPoints = controlPointDao.findAll(); //TODO good solution select by coordinate
//        }


        //Add control points
//        controlPointDTOs.addAll(aggregationControlPointsService.aggregateControlPoints(rightTopLatitude, rightTopLongitude,
//                leftBottomLatitude, leftBottomLongitude, controlPoints, zoom));


        if (lastGettingStationsFromDB == null
                || (Math.abs(lastGettingStationsFromDB.getTime() - new Date().getTime())/60)/1000 > 10) { //update data from db every 10 min
            lastGettingStationsFromDB = new Date();

            stations = stationDao.findAll();
        }

        controlPointDTOs.addAll(aggregationStationsService.aggregateStations(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, stations, zoom));

        return controlPointDTOs;
    }

    @Override
    public List<PointDTO> getPostsDTO(Double rightTopLatitude, Double rightTopLongitude,
                                      Double leftBottomLatitude, Double leftBottomLongitude,
                                      Integer zoom) {
        List<PointDTO> postDTOs = new LinkedList<>();
        if (lastGettingPostTracesFromDB == null
                || (Math.abs(lastGettingPostTracesFromDB.getTime() - new Date().getTime())/60)/1000 > 10) { //update data from db 10 min
            lastGettingPostTracesFromDB = new Date();
            lastPostTraces = postTracesDao.findLastPostTraces();
        }

        postDTOs.add(dtoService.getPointsDTOFromPosts(lastPostTraces));

        return postDTOs;
    }

    @Override
    public void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID) {
        for (Station station : stations) {
            for (ControlPoint controlPoint : station.getControlPoints()) {
                if (controlPoint.getId().equals(point.getPointID())) {

                    updateRegion(point, controlPoint);

                    if ("NOTHING".equals(point.getStatus())) {
                        controlPoint.setStatus(0);
                    }
                    if ("DETECT".equals(point.getStatus())) {
                        controlPoint.setStatus(1);
                    }
                    if ("MEASUREMENT".equals(point.getStatus())){
                        controlPoint.setStatus(2);
                    }
                    if ("NOTHING".equals(controlPoint.getStation().getStatus())) {
                        controlPoint.getStation().setStatus(0);
                    }
                    if ("DETECT".equals(controlPoint.getStation().getStatus())) {
                        controlPoint.getStation().setStatus(1);
                    }
                    if ("MEASUREMENT".equals(controlPoint.getStation().getStatus())){
                        controlPoint.getStation().setStatus(2);
                    }
                }
            }
        }
    }

    private void updateRegion(UpdatePointDTO point, ControlPoint controlPoint) {
        for (RegionDTO regionDto : regionDtos) {
            for (PostDTO postDTO : regionDto.getPostDTOs()) {
                for (RserviceDTO rserviceDTO : postDTO.getRserviceDTOs()) {
                    if (postDTO.getName().equals(controlPoint.getStation().getRservice().getName())) {
                        if (controlPoint.getStatus() != null) {
                            if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() - 1);
                            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено)
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() - 1);
                            }
                            if ("DETECT".equals(point.getStatus())) {
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
                            }
                            if ("MEASUREMENT".equals(point.getStatus())){
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + 1);
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public void updatePostLocation(Double coordLat, Double coordLon, Integer speed, Double direction,
                                   Integer postID, String type, Integer packetID) {
//        if (lastPostTraces.getId() == postID) {todo fix when use more one posts
            lastPostTraces.setLatitude(coordLat);
            lastPostTraces.setLongitude(coordLon);
            lastPostTraces.setSpeed(speed);
            lastPostTraces.setDirection(direction);
//        }
    }

    @Override
    public void updatePostStatus(Integer postID, String status) {
        for (Post post : posts) {
            if (post.getId().equals(postID)) {
                if ("OFFLINE".equals(status)) {
                    post.setState(1);
                } else if ("ONLINE".equals(status)) {
                    post.setState(0);
                }
            }
        }
    }
}
