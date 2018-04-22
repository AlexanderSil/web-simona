package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter @Setter
public class Region {
    private @Id Long id;

    private String regionName;

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
}
