package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by alex on 3/24/18.
 */
@Getter
@Setter
public class BaseStationDto {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String type;
}
