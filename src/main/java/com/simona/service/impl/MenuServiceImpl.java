package com.simona.service.impl;

import com.simona.model.*;
import com.simona.model.dto.*;
//import com.simona.service.AggregationControlPointsService;
import com.simona.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {


    @Autowired
    private DtoService dtoService;

    @Autowired
    private DaoService daoService;


    @Override
    public List<RegionDTO> getRegionsDTO() {

        List<RegionDTO> regionDTOs = daoService.getRegionDTOs();
        List<PostDTO> posts = daoService.getPostDTOs();

        //Add Rservices to Posts
        List<PostDTOTemp> postDTOTemps = new LinkedList<>();
        for (PostDTO post : posts) {
            PostDTOTemp postDTOTemp = new PostDTOTemp();
            postDTOTemp = dtoService.getPostDTOTemp(post);
            postDTOTemp.setRserviceDTOs(daoService.getRserviceDTOs());
            postDTOTemps.add(postDTOTemp);
        }
        for (RegionDTO regionDto : regionDTOs) {
            regionDto.setPostDTOTemps(postDTOTemps);
        }

        return regionDTOs;
    }

    @Override
    public List<RegionDTO> getActualRegions() {
        return daoService.getRegionDTOs();
    }

    @Override
    public void updatePostInfo(UpdatePointDTO newPoint, ControlPointDTO oldControlPointDTO, StationRserviceDTO rservice) {

        for (RegionDTO regionDto : daoService.getRegionDTOs()) {
            Integer detect = 0;
            Integer measurement = 0;
            for (PostDTOTemp postDTOTemp : regionDto.getPostDTOTemps()) {
                for (RserviceDTO rserviceDTO : postDTOTemp.getRserviceDTOs()) {
                    if (rserviceDTO.getName().equals(rservice.getRserviceTypesDTO().getName())) {
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


    @Override
    public void updatePostStatus(Integer postID, String status) {
        for (RegionDTO regionDto : daoService.getRegionDTOs()) {
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
}
