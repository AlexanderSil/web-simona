package com.simona.service.impl;

import com.simona.model.dto.*;
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
            postDTOTemp.setState(post.getState());
            postDTOTemps.add(postDTOTemp);
        }
        for (RegionDTO regionDto : regionDTOs) {
            regionDto.setPostDTOTemps(postDTOTemps);
        }
        daoService.setRegionDTOs(regionDTOs);
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
    public void updatePostInfoUnidentifiedCount(List<UnidentifiedStationDTO> unidentifiedStationDTOs) {
        for (UnidentifiedStationDTO unidentifiedStationDTO : unidentifiedStationDTOs) {
            for (RegionDTO regionDto : daoService.getRegionDTOs()) {
                for (PostDTOTemp postDTOTemp : regionDto.getPostDTOTemps()) {
                    for (RserviceDTO rserviceDTO : postDTOTemp.getRserviceDTOs()) {
                        if (rserviceDTO.getId().equals(unidentifiedStationDTO.getRservice())
                                || rserviceDTO.getId().equals(0)) {
                            rserviceDTO.setUnidentifiedcount(rserviceDTO.getUnidentifiedcount() + unidentifiedStationDTO.getCount());
                        }
                    }
                }
            }
        }
    }


    @Override
    public void updatePostStatus(Integer postID, String status) {
        List<PostDTO> postDTOs = daoService.getPostDTOs();

        for (PostDTO postDTO : postDTOs) {
            if (postDTO.getId().equals(postID)) {
                if ("OFFLINE".equals(status)) {
                    postDTO.setState(1);
                } else if ("ONLINE".equals(status)) {
                    postDTO.setState(0);
                }
                postDTO.setImageName(dtoService.getImageNameForPost(postDTO.getLastPostTraces().getDirection(), postDTO.getId()));
            }
        }
        getRegionsDTO();
    }
}
