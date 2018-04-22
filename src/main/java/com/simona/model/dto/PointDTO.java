package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * It is all viewed object(Control points, Posts)
 */
@Getter @Setter
public class PointDTO {

    public PointDTO(){}

    public PointDTO(Long id, Double latitude, Double longitude, String imageName) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageName = imageName;
    }

    private Long id;

    private Double latitude;
    private Double longitude;

    private String imageName; //todo use enum

    private String info;
}
