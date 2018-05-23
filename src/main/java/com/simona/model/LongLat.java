package com.simona.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LongLat implements Comparable  {

    private Double latitude;
    private Double longitude;

    @Override
    public int compareTo(Object o) {
        LongLat longLat = (LongLat)o;
        return Double.compare(latitude, longLat.getLatitude());
    }
}
