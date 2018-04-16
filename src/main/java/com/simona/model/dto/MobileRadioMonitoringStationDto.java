package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
@Component
@Getter
@Setter
public class MobileRadioMonitoringStationDto {
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

    private List<DetectedStationDto> detected;

    private String status; //todo use enum
    private String iconName; //todo use enum
}
