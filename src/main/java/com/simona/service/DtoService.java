package com.simona.service;

import com.simona.model.*;
import com.simona.model.dto.*;

import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
public interface DtoService {

    List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices);

    PostDTO getPostDTO(Post post);

    List<RegionDTO> getRegionDTOs(List<Region> regionList);

    PointDTO getPointDto(ControlPoint controlPoint);

    PointDTO getPointsFromPosts(PostTraces postTraces);

}
