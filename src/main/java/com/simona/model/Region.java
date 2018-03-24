package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@Entity@Table(name = "region")
@Getter @Setter
public class Region {
    private @Id Long id;

    private Double latitudeX;
    private Double longitudeX;
    private Double latitudeY;
    private Double longitudeY;

    @Transient
    private List<MobileRadioMonitoringStation> mobileRadioMonitoringStations;
}
