package com.simona.service.impl;

import com.simona.model.*;
import com.simona.model.dto.*;
import com.simona.service.DtoService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
@Service
public class DtoServiceImpl implements DtoService {

    @Override
    public List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices) {
        List<RserviceDTO> rserviceDTOs = new LinkedList<>();

        for (Rservice rservice : rservices) {
            if (rservice.getStations().size() >= 1) {
                RserviceDTO rserviceDTO = new RserviceDTO();

                rserviceDTO.setName(rservice.getName());
                rserviceDTO.setCount(rservice.getStations().size());

                rserviceDTOs.add(rserviceDTO);
            }
        }

        return rserviceDTOs;
    }

    @Override
    public PostDTO getPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setState(post.getState());
        postDTO.setIconName("greenCarРМ-1500-Р3:5М.png");//todo hardcode
        postDTO.setName("РМ-1500-Р3/5М");//todo hardcode
        return postDTO;
    }

    @Override
    public List<RegionDTO> getRegionDTOs(List<Region> regionList) {
        List<RegionDTO> regionDTOS = new LinkedList<>();
        if (regionList != null && !regionList.isEmpty()) {
            for (Region region : regionList) {
                RegionDTO regionDTO = new RegionDTO();

                regionDTO.setId(region.getId());

                regionDTO.setName(region.getRegionName());

                regionDTO.setLatitudeX(region.getLatitudeX());
                regionDTO.setLongitudeX(region.getLongitudeX());

                regionDTO.setLatitudeY(region.getLatitudeY());
                regionDTO.setLongitudeY(region.getLongitudeY());

                regionDTOS.add(regionDTO);
            }
        }
        return regionDTOS;
    }

    public PointDTO getPointDto(ControlPoint controlPoint) {
        PointDTO pointDTO = new PointDTO();

        pointDTO.setLongitude(controlPoint.getStation().getLongitude());
        pointDTO.setLatitude(controlPoint.getStation().getLatitude());

        String info = "";
        if (controlPoint.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
            pointDTO.setImageName("grey.png");//todo use triangle
            info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ controlPoint.getStation().getNick_name() +" </b></p>";
        } else if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
            pointDTO.setImageName("yellow.png");//todo use triangle
            info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
        } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
            pointDTO.setImageName("green.png");//todo use triangle
            info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
        }

        pointDTO.setInfo(info);
        return pointDTO;
    }

    @Override
    public PointDTO getPointsFromPosts(PostTraces postTraces) {

        if (postTraces != null) {
            PointDTO pointDTO = new PointDTO();

            pointDTO.setLongitude(postTraces.getLongitude());
            pointDTO.setLatitude(postTraces.getLatitude());

            if (postTraces.getDirection() != null) {
                if (337.5d <= postTraces.getDirection() && postTraces.getDirection() < 22.5d){
                    pointDTO.setImageName("pointer/0.png");
                }
                if (22.5d <= postTraces.getDirection() && postTraces.getDirection() < 67.5d){
                    pointDTO.setImageName("pointer/45.png");
                }
                if (67.5d <= postTraces.getDirection() && postTraces.getDirection() < 112.5d){
                    pointDTO.setImageName("pointer/90.png");
                }
                if (112.5d <= postTraces.getDirection() && postTraces.getDirection() < 157.5d){
                    pointDTO.setImageName("pointer/135.png");
                }
                if (157.5d <= postTraces.getDirection() && postTraces.getDirection() < 202.5d){
                    pointDTO.setImageName("pointer/180.png");
                }
                if (202.5d <= postTraces.getDirection() && postTraces.getDirection() < 247.5d){
                    pointDTO.setImageName("pointer/225.png");
                }
                if (247.5d <= postTraces.getDirection() && postTraces.getDirection() < 292.5d){
                    pointDTO.setImageName("pointer/270.png");
                }
                if (292.5d <= postTraces.getDirection() && postTraces.getDirection() < 337.5d){
                    pointDTO.setImageName("pointer/315.png");
                }
            } else {
                pointDTO.setImageName("pointer/0.png");
            }
            pointDTO.setInfo("Пост ID: " + postTraces.getPost().getId());

            if (postTraces.getSpeed() != null) {
                pointDTO.setInfo(pointDTO.getInfo() + ". Скорость: " + postTraces.getSpeed().toString() + "km/h");
            }

            return pointDTO;
        }
        return null;
    }
}
