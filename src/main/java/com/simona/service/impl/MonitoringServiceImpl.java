package com.simona.service.impl;

import com.simona.dao.*;
import com.simona.model.*;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.service.AggregationControlPointsService;
import com.simona.service.DtoService;
import com.simona.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by alex on 3/19/18.
 */
@Service
public class MonitoringServiceImpl implements MonitoringService {


    private static final Logger LOGGER = Logger.getLogger(MonitoringServiceImpl.class.getName());

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

    @Override
    public List<RegionDTO> getRegionsDTO() {

        //Add regions
        List<Region> regionList = daoMock.findAllRegions();

        Date startGetDataFromDB = new Date();
        Iterable<Post> posts = postDao.findAll();
        Iterable<Rservice> rservices = rserviceDao.findAll();
        long timeConnection = Math.abs(startGetDataFromDB.getTime() - new Date().getTime());
        LOGGER.info("Time connection and get(Post, Rservice) data from db -  " + timeConnection/1000 + " sec.");

        //Add Rservices to Posts
        List<PostDTO> postDTOs = new LinkedList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            postDTO = dtoService.getPostDTO(post);
            postDTO.setRserviceDTOs(dtoService.getRserviceDTOs(rservices));
            postDTOs.add(postDTO);
        }

        List<RegionDTO> regionDtos = dtoService.getRegionDTOs(regionList);
        for (RegionDTO regionDto : regionDtos) {
            regionDto.setPostDTOs(postDTOs);
        }

        return regionDtos;
    }

    protected static Random random = new Random();


    public List<PointDTO> getAggregatedPointDTO(Double rightTopLatitude, Double rightTopLongitude,
                                                Double leftBottomLatitude, Double leftBottomLongitude,
                                                Integer zoom, List<String> regions, List<String> posts) {

        Date startGetDataFromDB = new Date();
        Iterable<ControlPoint> controlPoints = controlPointDao.findAll(); //TODO select by coordinate station
        long timeConnection = Math.abs(startGetDataFromDB.getTime() - new Date().getTime());
        LOGGER.info("Time connection and get(ControlPoint) data from db - " + timeConnection/1000 + " sec.");

        List<PointDTO> controlPointDTOs = new LinkedList<>();

        //Add post
        PostTraces lastPostTraces = postTracesDao.findLastPostTraces();
        controlPointDTOs.add(dtoService.getPointsFromPosts(lastPostTraces));


        //Add control points
        controlPointDTOs.addAll(aggregationControlPointsService.aggregateControlPoints(controlPoints, zoom));

        return controlPointDTOs;
    }


}
