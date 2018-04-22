package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class RegionDTO {
    private Long id;
    private String name;

    /**
     * Right top
     */
    private Double latitudeX;
    private Double longitudeX;

    /**
     * Left bottom
     */
    private Double latitudeY;
    private Double longitudeY;

    private List<PostDTO> postDTOs;
}
