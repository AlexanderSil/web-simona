package com.simona.service;

import com.simona.model.*;
import com.simona.model.dto.*;

import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
public interface DtoService {

    List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices, List<ControlPoint> controlPoints);

    PostDTOTemp getPostDTO(Post post);

    List<RegionDTO> getRegionDTOs(List<Region> regionList);

    PointDTO getPointDto(ControlPoint controlPoint);

    PointDTO getPointDto(StationDTO stationDTO);

    PointDTO getPointsDTOFromPosts(PostTraces postTraces);

    List<StationDTO> getStationDTOs(Iterable<Station> stations);

    List<PostDTO> getPostDTOs(Iterable<Post> posts, PostTraces lastPostTraces);

    String getImageNameForPost(Double direction);

}
