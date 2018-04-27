//package com.simona.service.impl;
//
//import com.simona.controller.MonitoringController;
//import com.simona.model.ControlPoint;
//import com.simona.model.LongLat;
//import com.simona.model.Station;
//import com.simona.model.dto.PointDTO;
//import com.simona.service.AggregationStationsService;
//import com.simona.service.DtoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.logging.Logger;
//
//@Service
//public class AggregationStationsServiceImpl implements AggregationStationsService {
//
//    private static final Logger LOGGER = Logger.getLogger(MonitoringController.class.getName());
//
//    @Autowired
//    private DtoService dtoService;
//
//    @Override
//    public List<PointDTO> aggregateStations(Iterable<Station> stations, Integer zoom) {
//
//        Map<LongLat, List> aggrMap = new HashMap<>();
//        int countStation = 0;
//        for (Station station : stations) {
//            countStation++;
////            if (leftBottomLatitude < baseStation.getLatitude() && baseStation.getLatitude() < rightTopLatitude
////                    && leftBottomLongtitude < baseStation.getLongitude() && baseStation.getLongitude() < rightTopLongtitude) {
//            if (aggrMap.isEmpty()) {
//                addToAggrMap(aggrMap, station);
//            } else {
//                boolean aggregated = false;
//                for (LongLat longLat : aggrMap.keySet()) {
//                    if (zoom == 21 && distance(station, longLat, "K") <= 0.005) {//change to lower distance //0.0045011109739724245
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 20 && distance(station, longLat, "K") <= 0.01) {//change to lower distance //0.009400531899557338
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 19 && distance(station, longLat, "K") <= 0.02) {//change to lower distance //0.01632482771763881
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 18 && distance(station, longLat, "K") <= 0.04) {//change to lower distance //0.03223491895414472
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 17 && distance(station, longLat, "K") <= 0.07) {//change to lower distance //0.06856566195963213
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 16 && distance(station, longLat, "K") <= 0.12) {//change to lower distance //0.0701160466652148
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 15 && distance(station, longLat, "K") <= 0.22) {//change to lower distance //0.1377618234489109
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 14 && distance(station, longLat, "K") <= 0.45) {//0.20088793859573414 !
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 13 && distance(station, longLat, "K") <= 1.0) {//change to higher distance //0.5784220345040679
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 12 && distance(station, longLat, "K") <= 2.1) {//change to higher distance //1.1375660772017505
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 11 && distance(station, longLat, "K") <= 3.9) {//change to higher distance //2.2913646610699744
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 10 && distance(station, longLat, "K") <= 6.5) {//change to higher distance //4.542713975147452
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 9 && distance(station, longLat, "K") <= 13.0) {//change to higher distance //8.59961892760351
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 8 && distance(station, longLat, "K") <= 24.0) {//change to higher distance //17.843863090989773
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 7 && distance(station, longLat, "K") <= 48.0) {//change to higher distance //36.84453815479292
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    } else if (zoom == 6 && distance(station, longLat, "K") <= 90.0) {//change to higher distance //71.29392669091827
//                        aggrMap.get(longLat).add(station);
//                        aggregated = true;
//                    }
//                }
//                if (!aggregated) {
//                    addToAggrMap(aggrMap, station);
//                }
//            }
////            }
//        }
//        LOGGER.info("Stations count - " + countStation);
//        int countCP = 0;
//        List<PointDTO> pointDTOS = new LinkedList<>();
//        for (LongLat longLat : aggrMap.keySet()) {
//            List<Station> stationList = aggrMap.get(longLat);
//            for (Station station : stationList) {
//                List<ControlPoint> controlPointList = new LinkedList<>();
//                controlPointList.addAll(station.getControlPoints());
//                countCP = countCP + controlPointList.size();
//
//                if (controlPointList.size() > 1) {
//                    pointDTOS.add(aggregateControlPoint(controlPointList));
//                } else {
//                    PointDTO pointDTO = dtoService.getPointDto(controlPointList.get(0));
//                    pointDTOS.add(pointDTO);
//                }
//            }
//        }
//        LOGGER.info("ControlPoints count - " + countCP);
//
//
//        return pointDTOS;
//    }
//    private void addToAggrMap(Map<LongLat, List> myMap, Station station) {
//        LongLat longLat = new LongLat();
//        longLat.setLatitude(station.getLatitude());
//        longLat.setLongitude(station.getLongitude());
//        List<Station> stations = new LinkedList<>();
//        stations.add(station);
//        myMap.put(longLat, stations);
//    }
//
//    /**
//     * @param unit "M" - Miles; "K" - Kilometers; "N" - Nautical Miles
//     */
//    private double distance(Station station, LongLat longLat, String unit) {
//        double lat1 = station.getLatitude();
//        double lon1 = station.getLongitude();
//        double lat2 = longLat.getLatitude();
//        double lon2 = longLat.getLongitude();
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        if ("K".equals(unit)) {
//            dist = dist * 1.609344;
//        } else if ("N".equals(unit)) {
//            dist = dist * 0.8684;
//        }
//        return (dist);
//    }
//
//    private PointDTO aggregateControlPoint(List<ControlPoint> controlPoints) {
//        PointDTO pointDTO = new PointDTO();
//
//        LongLat longLat = new LongLat();
//        String info = "";
//        boolean grey = false;
//        boolean green = false;
//        boolean yellow = false;
//        for (ControlPoint controlPoint : controlPoints) {
//            if (longLat.getLongitude() == null) {
//                longLat.setLatitude(controlPoint.getStation().getLatitude());
//                longLat.setLongitude(controlPoint.getStation().getLongitude());
//            } else {
//                longLat.setLatitude(longLat.getLatitude() + controlPoint.getStation().getLatitude());
//                longLat.setLongitude(longLat.getLongitude() + controlPoint.getStation().getLongitude());
//            }
//
//            if (controlPoint.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
//                grey = true;
//                info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ controlPoint.getStation().getNick_name() +" </b></p>";
//            } else if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
//                yellow = true;
//
//                info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
//            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
//                green = true;
//                info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
//            }
//
//        }
//        pointDTO.setLatitude(longLat.getLatitude()/controlPoints.size());
//        pointDTO.setLongitude(longLat.getLongitude()/controlPoints.size());
//
//        if (grey && green && yellow) {
//            pointDTO.setImageName("grey_yellow_green.png");
//        } else if (grey && green && !yellow) {
//            pointDTO.setImageName("grey_green.png");
//        } else if (grey && !green && yellow) {
//            pointDTO.setImageName("grey_yellow.png");
//        } else if (!grey && green && yellow) {
//            pointDTO.setImageName("yellow_green.png");
//        } else if (grey && !green && !yellow) {
//            pointDTO.setImageName("grey.png");
//        } else if (!grey && !green && yellow) {
//            pointDTO.setImageName("yellow.png");
//        } else if (!grey && green && !yellow) {
//            pointDTO.setImageName("green.png");
//        }
//
//
//        pointDTO.setInfo(info);
//        return pointDTO;
//    }
//    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//    /*::  This function converts decimal degrees to radians             :*/
//    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//    /*::  This function converts radians to decimal degrees             :*/
//    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }
//
//
//}
