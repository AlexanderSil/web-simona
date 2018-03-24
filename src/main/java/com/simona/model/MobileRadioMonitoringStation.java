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

    private Double latitude;
    private Double longitude;

    private String type;

    @Transient
    private List<BaseStation> baseStations;
}
