package com.simona.service;

import com.simona.model.*;
import com.simona.model.dto.*;

import java.util.List;

public interface DtoService {

    List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices, List<StationDTO> stationDTOS);

    PostDTOTemp getPostDTOTemp(PostDTO post);

    List<RegionDTO> getRegionDTOs(List<Region> regionList);

    PointDTO getPointDto(ControlPoint controlPoint);

    PointDTO getPointDto(StationDTO stationDTO);

    PointDTO getPointsDTOFromPosts(PostTraces postTraces);

    List<StationDTO> getStationDTOs(Iterable<Station> stations);

    List<PostDTO> getPostDTOs(Iterable<Post> posts);

    String getImageNameForPost(Double direction);

}
