package com.simona.service.impl;

import com.simona.dao.*;
import com.simona.model.*;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.UpdatePointDTO;
import com.simona.service.AggregationControlPointsService;
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

    @Autowired
    private AggregationControlPointsService aggregationControlPointsService;

    @Autowired
    private ControlPointDao controlPointDao;

    @Autowired
    private RserviceDao rserviceDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostTracesDao postTracesDao;

    private Date lastGettingControlPointFromDB;
    private Iterable<ControlPoint> controlPoints;

    private Date lastGettingPostTracesFromDB;
    private PostTraces lastPostTraces;

    private Date lastGettingPostsFromDB;
    private Iterable<Post> posts;

    private Date lastGettingRservicesFromDB;
    private Iterable<Rservice> rservices;

    @Override
    public List<RegionDTO> getRegionsDTO() {

        //Add regions
        List<Region> regionList = daoMock.findAllRegions();
        List<RegionDTO> regionDtos = dtoService.getRegionDTOs(regionList);

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


    public List<PointDTO> getAggregatedPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom) {
        List<PointDTO> controlPointDTOs = new LinkedList<>();

        if (lastGettingControlPointFromDB == null
                || (Math.abs(lastGettingControlPointFromDB.getTime() - new Date().getTime())/60)/1000 > 60) { //update data from db 1 hour
            lastGettingControlPointFromDB = new Date();
            controlPoints = controlPointDao.findAll(); //TODO good solution select by coordinate
        }

        if (lastGettingPostTracesFromDB == null
                || (Math.abs(lastGettingPostTracesFromDB.getTime() - new Date().getTime())/60)/1000 > 60) { //update data from db 1 hour
            lastGettingPostTracesFromDB = new Date();
            lastPostTraces = postTracesDao.findLastPostTraces();
        }

        controlPointDTOs.add(dtoService.getPointsFromPosts(lastPostTraces));

        //Add control points
        controlPointDTOs.addAll(aggregationControlPointsService.aggregateControlPoints(rightTopLatitude, rightTopLongitude,
                leftBottomLatitude, leftBottomLongitude, controlPoints, zoom));

        return controlPointDTOs;
    }

//    public MonitoringObjects getMonitoringObjects() {
//        if (zoom == null) {
//            zoom = 11;
//        }
//        MonitoringObjects monitoringObjects = new MonitoringObjects();
//        if (rservices == null && posts == null && controlPoints == null && lastPostTraces == null) {
//            monitoringObjects.setName("MonitoringObjects == null");
//        } else {
//            monitoringObjects.setName("MonitoringObjects != null");
//        }
//
//        if (rservices != null && posts != null)
//            monitoringObjects.setRegionDTOs(getRegionsDTO());
//        //set control points
//        if (controlPoints != null)
//            monitoringObjects.setPointDTOs(aggregationControlPointsService.aggregateControlPoints(rightTopLatitude, rightTopLongitude,
//                    leftBottomLatitude, leftBottomLongitude, controlPoints, zoom));
//
//        //set post
//        if (lastPostTraces != null)
//            monitoringObjects.getPointDTOs().add(dtoService.getPointsFromPosts(lastPostTraces));

//        return monitoringObjects;
//    }

    @Override
    public void updateControlPoint(UpdatePointDTO point, Integer postID, String type, Integer packetID) {
        for (ControlPoint controlPoint : controlPoints) {
            if (controlPoint.getId() == point.getPointID()) {
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
}
