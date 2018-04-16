package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alex on 3/19/18.
 */
@Entity
@Table(name = "base_station")
@Getter @Setter
public class BaseStation {

    public BaseStation() {}

    public BaseStation(Long id, Double latitude, Double longitude, String iconName) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iconName = iconName;
    }

    @Id
    @GeneratedValue
    private Long id;

    private Long mobileRadioMonitoringStationId;

    private Double latitude;
    private Double longitude;

    private String iconName;

}
