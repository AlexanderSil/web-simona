package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@Entity
@Table(name = "mobile_radio_monitoring_station")
@Getter @Setter
public class MobileRadioMonitoringStation {
    @Id
    @GeneratedValue
    private Long id;

    private Long regionId;

    private String nameStation;

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

    private String status;

    private String iconName;

    @Transient
    private List<BaseStation> baseStations;
}
