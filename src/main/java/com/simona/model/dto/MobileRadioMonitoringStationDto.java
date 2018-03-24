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

    private Double latitude;
    private Double longitude;

    private String type;

    private List<BaseStationDto> baseStations;
}
