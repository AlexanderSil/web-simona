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
        return Double.compare(latitude + longitude, longLat.getLatitude() + longLat.getLongitude());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LongLat)) {
            return false;
        }

        LongLat longLat = (LongLat) o;

        int result1 = Double.compare(latitude, longLat.getLatitude());
        int result2 = Double.compare(longitude, longLat.getLongitude());

        return result1 == 0 && result2 == 0;
    }
}
