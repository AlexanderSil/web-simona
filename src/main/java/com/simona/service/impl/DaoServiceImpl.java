package com.simona.service.impl;

import com.simona.dao.DaoMock;
import com.simona.dao.PostDao;
import com.simona.dao.RserviceDao;
import com.simona.dao.StationDao;
import com.simona.model.Post;
import com.simona.model.Region;
import com.simona.model.Rservice;
import com.simona.model.Station;
import com.simona.model.dto.PostDTO;
import com.simona.model.dto.RegionDTO;
import com.simona.model.dto.RserviceDTO;
import com.simona.model.dto.StationDTO;
import com.simona.service.DaoService;
import com.simona.service.DtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DaoServiceImpl implements DaoService{

    @Autowired
    private StationDao stationDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private PostDao postDao;

    @Autowired
    private RserviceDao rserviceDao;

    @Autowired
    private DaoMock daoMock;

    private List<RegionDTO> regionDtos = new LinkedList<>();

    private List<PostDTO> postDTOs = new LinkedList<>();

    private Set<StationDTO> stationDTOs = new TreeSet<>();

    private List<RserviceDTO> rservicesDTOs = new ArrayList<>();

    private Boolean waitWhenLoadedStationDTO = true;
    private Boolean waitWhenLoadedPostDTO = true;
    private Boolean waitWhenLoadedRegionDTO = true;
    private Boolean waitWhenLoadedRserviceDTO = true;

    public Set<StationDTO> getStationDTOs() {
        while (waitWhenLoadedStationDTO) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Wait when wait When Loaded List Station from DB.");
        }
        return stationDTOs;
    }

    public List<PostDTO> getPostDTOs() {
        while (waitWhenLoadedPostDTO) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Wait when wait When Loaded List Post from DB.");
        }
        return postDTOs;
    }

    @Override
    public List<RegionDTO> getRegionDTOs() {
        while (waitWhenLoadedRegionDTO) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Wait when wait When Loaded List Region from DB.");
        }
        return regionDtos;
    }

    @Override
    public List<RserviceDTO> getRserviceDTOs() {
        while (waitWhenLoadedRserviceDTO) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Wait when wait When Loaded List Rservice from DB.");
        }
        return rservicesDTOs;
    }

    @Scheduled(fixedRate = 10*60*1000)//10 min
    @Transactional
    public void scheduledSelectAllDateFromDB(){
        log.info("Start scheduled Loaded data from DB.");
        //Get List RegionDTO
        List<Region> regionList = daoMock.findAllRegions();
        regionDtos = dtoService.getRegionDTOs(regionList);
        waitWhenLoadedRegionDTO = false;

        //Get List StationDTO
        Iterable<Station> stations = stationDao.findAll();
        stationDTOs = dtoService.getStationDTOs(stations);
        waitWhenLoadedStationDTO = false;

        //Get List RserviceDTO
        Iterable<Rservice> rservices = rserviceDao.findAll();
        rservicesDTOs = dtoService.getRserviceDTOs(rservices, getStationDTOs());
        waitWhenLoadedRserviceDTO = false;

        //Get List PostDTO
        List<Integer> mrmsNames = new LinkedList<>();
        mrmsNames.add(28);
        Iterable<Integer> iterable = mrmsNames;
        Iterable<Post> posts = postDao.findAllById(iterable);
        postDTOs = dtoService.getPostDTOs(posts);
        waitWhenLoadedPostDTO = false;

        log.info("And scheduled Loaded data from DB.");
    }
}
