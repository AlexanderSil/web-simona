package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * It is for show in menu
 */
@Getter
@Setter
public class PostDTO {

    private Integer id;

    private String name;

    private Integer state;
    private String iconName; //todo use enum

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

    private List<RserviceDTO> rserviceDTOs;
}
