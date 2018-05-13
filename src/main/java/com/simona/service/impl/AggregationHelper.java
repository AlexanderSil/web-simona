package com.simona.service.impl;


import com.simona.model.ControlPoint;
import com.simona.model.LongLat;
import com.simona.model.Station;


public class AggregationHelper {


    /**
     * @param unit "M" - Miles; "K" - Kilometers; "N" - Nautical Miles
     */
    public static double distance(ControlPoint controlPoint, LongLat longLat, String unit) {
        double lat1 = controlPoint.getStation().getLatitude();
        double lon1 = controlPoint.getStation().getLongitude();
        double lat2 = longLat.getLatitude();
        double lon2 = longLat.getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if ("K".equals(unit)) {
            dist = dist * 1.609344;
        } else if ("N".equals(unit)) {
            dist = dist * 0.8684;
        }
        return (dist);
    }


    /**
     * @param unit "M" - Miles; "K" - Kilometers; "N" - Nautical Miles
     */
    public static double distance(Station station, LongLat longLat, String unit) {
        double lat1 = station.getLatitude();
        double lon1 = station.getLongitude();
        double lat2 = longLat.getLatitude();
        double lon2 = longLat.getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if ("K".equals(unit)) {
            dist = dist * 1.609344;
        } else if ("N".equals(unit)) {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
