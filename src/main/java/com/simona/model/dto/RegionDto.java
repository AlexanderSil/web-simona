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
public class RegionDto {
    private Long id;
    private Double latitudeX;
    private Double longitudeX;
    private Double latitudeY;
    private Double longitudeY;
    private List<MobileRadioMonitoringStationDto> mobileRadioMonitoringStations;
}
