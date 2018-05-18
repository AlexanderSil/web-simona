package com.simona.service.impl;

import com.simona.dao.*;
import com.simona.model.*;
import com.simona.model.dto.*;
//import com.simona.service.AggregationControlPointsService;
import com.simona.service.ControlPointsService;
import com.simona.service.DtoService;
import com.simona.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private DaoMock daoMock;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private ControlPointDao controlPointDao;

    @Autowired
    private RserviceDao rserviceDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private ControlPointsService controlPointsService;

    private List<RegionDTO> regionDtos = new LinkedList<>();

    @Override
    public List<RegionDTO> getRegionsDTO() {
        regionDtos = new LinkedList<>();

        //Add regions
        List<Region> regionList = daoMock.findAllRegions();
        regionDtos = dtoService.getRegionDTOs(regionList);

        Iterable<Post> posts = postDao.findAll();
        Iterable<Rservice> rservices = rserviceDao.findAll();
        Iterable<ControlPoint> controlPoints = controlPointDao.findAll();

        //Add Rservices to Posts
        List<PostDTOTemp> postDTOTemps = new LinkedList<>();
        for (Post post : posts) {
            PostDTOTemp postDTOTemp = new PostDTOTemp();
            postDTOTemp = dtoService.getPostDTO(post);
            postDTOTemp.setRserviceDTOs(dtoService.getRserviceDTOs(rservices, toList(controlPoints)));
            postDTOTemps.add(postDTOTemp);
        }
        for (RegionDTO regionDto : regionDtos) {
            regionDto.setPostDTOTemps(postDTOTemps);
        }

        return regionDtos;
    }

    @Override
    public List<RegionDTO> getActualRegions() {
        return regionDtos;
    }

    @Override
    public void updatePostInfo(UpdatePointDTO newPoint, ControlPointDTO oldControlPointDTO, StationRserviceDTO rservice) {

        for (RegionDTO regionDto : regionDtos) {
            Integer detect = 0;
            Integer measurement = 0;
            for (PostDTOTemp postDTOTemp : regionDto.getPostDTOTemps()) {
                for (RserviceDTO rserviceDTO : postDTOTemp.getRserviceDTOs()) {
                    if (rserviceDTO.getName().equals(rservice.getName())) {
                        if (oldControlPointDTO.getStatus() != null) {
                            if (oldControlPointDTO.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() - 1);
                                detect = detect - 1;
                            } else if (oldControlPointDTO.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено)
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() - 1);
                                measurement = measurement - 1;
                            }
                        }
                        if (newPoint.getStatus() != null) {
                            if (newPoint.getStatus().equals("DETECT")) {// желтого цвета – РЭС выявлена (Обнаружено)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
                                detect = detect + 1;
                            } else if (newPoint.getStatus().equals("MEASUREMENT")) {// зеленого цвета – РЭС выявлена и измерена (Измерено)
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + 1);
                                measurement = measurement + 1;
                            }
                        }
                    }
                }
                for (RserviceDTO rserviceDTO : postDTOTemp.getRserviceDTOs()) {
                    if (rserviceDTO.getName().equals("Всего:")) {
                        rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + detect);
                        rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + measurement);
                    }
                }
            }
        }
    }

    //
// @Override
//    public List<PostDTOTemp> getActualRegions(List<Integer> mrmsNames) {
////    public List<PostDTOTemp> getActualRegions(UpdatePointDTO point, ControlPointDTO controlPointDTO, String nameStation) {
//        for (RegionDTO regionDto : regionDtos) {
//            for (PostDTOTemp postDTOTemp : regionDto.getPostDTOTemps()) {
//                for (RserviceDTO rserviceDTO : postDTOTemp.getRserviceDTOs()) {
//                    if (postDTOTemp.getName().equals(nameStation)) {
//                        if (controlPointDTO.getStatus() != null) {
//                            if (controlPointDTO.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено)
//                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() - 1);
//                            } else if (controlPointDTO.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено)
//                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() - 1);
//                            }
//                            if ("DETECT".equals(point.getStatus())) {
//                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
//                            }
//                            if ("MEASUREMENT".equals(point.getStatus())){
//                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + 1);
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//        return regionDtos.get(0).getPostDTOTemps();//todo change when use more one region
//    }

    @Override
    public void updatePostStatus(Integer postID, String status) {
        for (RegionDTO regionDto : regionDtos) {
            for (PostDTOTemp postDTOTemp : regionDto.getPostDTOTemps()) {
                if (postDTOTemp.getId().equals(postID)) {
                    if ("OFFLINE".equals(status)) {
                        postDTOTemp.setState(1);
                    } else if ("ONLINE".equals(status)) {
                        postDTOTemp.setState(0);
                    }
                }
            }
        }
    }

    private static <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }

}
