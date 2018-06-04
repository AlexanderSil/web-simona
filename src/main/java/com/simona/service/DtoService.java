package com.simona.service;

import com.simona.model.*;
import com.simona.model.dto.*;

import java.util.List;
import java.util.Set;

public interface DtoService {

    List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices, Set<StationDTO> stationDTOS);

    PostDTOTemp getPostDTOTemp(PostDTO post);

    List<RegionDTO> getRegionDTOs(List<Region> regionList);

//    PointDTO getPointDto(ControlPoint controlPoint);

    PointDTO getPointDto(Set<StationDTO> stationDTOS);

    Set<StationDTO> getStationDTOs(Iterable<Station> stations);

    List<PostDTO> getPostDTOs(Iterable<Post> posts);

    String getImageNameForPost(Double direction, Integer postId);

}
