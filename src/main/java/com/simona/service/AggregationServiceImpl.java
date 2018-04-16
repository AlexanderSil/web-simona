package com.simona.service;

import com.simona.model.BaseStation;
import com.simona.model.LongLat;
import com.simona.model.dto.BaseStationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by alex on 4/1/18.
 */
@Service
public class AggregationServiceImpl implements AggregationService {

    @Autowired
    private DtoService dtoService;

    @Override
    public List<BaseStationDto> aggregate(List<BaseStation> baseStationList, Integer zoom, Double rightTopLatitude, Double rightTopLongtitude,
                                          Double leftBottomLatitude, Double leftBottomLongtitude) {

        int count = 0;
        Map<LongLat, List> myMap = new HashMap<>();
        for (BaseStation baseStation : baseStationList) {
            if (leftBottomLatitude < baseStation.getLatitude() && baseStation.getLatitude() < rightTopLatitude
                    && leftBottomLongtitude < baseStation.getLongitude() && baseStation.getLongitude() < rightTopLongtitude) {
                count = count + 1;
                if (myMap.isEmpty()) {
                    LongLat longLat = new LongLat();
                    longLat.setLatitude(baseStation.getLatitude());
                    longLat.setLongitude(baseStation.getLongitude());
                    List<BaseStation> baseStations = new LinkedList<>();
                    baseStations.add(baseStation);
                    myMap.put(longLat, baseStations);
                } else {
                    boolean aggregated = false;
                    for (LongLat longLat : myMap.keySet()) {
                        if (zoom == 21 && distance(baseStation, longLat, "K") <= 0.005) {//change to lower distance //0.0045011109739724245
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 20 && distance(baseStation, longLat, "K") <= 0.01) {//change to lower distance //0.009400531899557338
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 19 && distance(baseStation, longLat, "K") <= 0.02) {//change to lower distance //0.01632482771763881
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 18 && distance(baseStation, longLat, "K") <= 0.04) {//change to lower distance //0.03223491895414472
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 17 && distance(baseStation, longLat, "K") <= 0.07) {//change to lower distance //0.06856566195963213
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 16 && distance(baseStation, longLat, "K") <= 0.12) {//change to lower distance //0.0701160466652148
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 15 && distance(baseStation, longLat, "K") <= 0.22) {//change to lower distance //0.1377618234489109
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 14 && distance(baseStation, longLat, "K") <= 0.45) {//0.20088793859573414 !
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 13 && distance(baseStation, longLat, "K") <= 1.0) {//change to higher distance //0.5784220345040679
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 12 && distance(baseStation, longLat, "K") <= 2.1) {//change to higher distance //1.1375660772017505
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 11 && distance(baseStation, longLat, "K") <= 3.9) {//change to higher distance //2.2913646610699744
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 10 && distance(baseStation, longLat, "K") <= 6.5) {//change to higher distance //4.542713975147452
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 9 && distance(baseStation, longLat, "K") <= 13.0) {//change to higher distance //8.59961892760351
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 8 && distance(baseStation, longLat, "K") <= 24.0) {//change to higher distance //17.843863090989773
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 7 && distance(baseStation, longLat, "K") <= 48.0) {//change to higher distance //36.84453815479292
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        } else if (zoom == 6 && distance(baseStation, longLat, "K") <= 90.0) {//change to higher distance //71.29392669091827
                            myMap.get(longLat).add(baseStation);
                            aggregated = true;
                        }
                    }
                    if (!aggregated) {
                        LongLat longLat = new LongLat();
                        longLat.setLatitude(baseStation.getLatitude());
                        longLat.setLongitude(baseStation.getLongitude());
                        List<BaseStation> baseStations = new LinkedList<>();
                        baseStations.add(baseStation);
                        myMap.put(longLat, baseStations);
                    }
                }
            }
        }

        List<BaseStationDto> baseStationDtoList = new LinkedList<>();
        for (LongLat longLat : myMap.keySet()) {
            List<BaseStation> list = myMap.get(longLat);
            if (list.size() > 1) {
                baseStationDtoList.add(aggregate(list));
            } else {
                BaseStationDto baseStationDto = dtoService.getBaseStationDto(list.get(0));
                baseStationDtoList.add(baseStationDto);
                //todo do info
            }
        }


        return baseStationDtoList;
    }

    private BaseStationDto aggregate(List<BaseStation> baseStations) {
        BaseStationDto baseStationDto = new BaseStationDto();

        LongLat longLat = new LongLat();
        int countGrey = 0;
        int countGreen = 0;
        int countYellow = 0;
        boolean grey = false;
        boolean green = false;
        boolean yellow = false;
        for (BaseStation baseStation : baseStations) {
            if (longLat.getLongitude() == null) {
                longLat.setLatitude(baseStation.getLatitude());
                longLat.setLongitude(baseStation.getLongitude());
            } else {
                longLat.setLatitude(longLat.getLatitude() + baseStation.getLatitude());
                longLat.setLongitude(longLat.getLongitude() + baseStation.getLongitude());
            }

            if ("grey.png".equals(baseStation.getIconName())) {
                grey = true;
                countGrey++;
            }
            if ("green.png".equals(baseStation.getIconName())) {
                green = true;
                countGreen++;
            }
            if ("yellow.png".equals(baseStation.getIconName())) {
                yellow = true;
                countYellow++;
            }

        }
        baseStationDto.setLatitude(longLat.getLatitude()/baseStations.size());
        baseStationDto.setLongitude(longLat.getLongitude()/baseStations.size());

        if (grey && green && yellow) {
            baseStationDto.setImageName("grey_yellow_green.png");
        } else if (grey && green && !yellow) {
            baseStationDto.setImageName("grey_green.png");
        } else if (grey && !green && yellow) {
            baseStationDto.setImageName("grey_yellow.png");
        } else if (!grey && green && yellow) {
            baseStationDto.setImageName("yellow_green.png");
        } else if (grey && !green && !yellow) {
            baseStationDto.setImageName("grey.png");
        } else if (!grey && !green && yellow) {
            baseStationDto.setImageName("yellow.png");
        } else if (!grey && green && !yellow) {
            baseStationDto.setImageName("green.png");
        }


        String[] info = {green?"Выявленные и измеренные РЭС - " + countGreen:"", yellow?"Выявленные, но не измеренные РЭС - " + countYellow:"",
                grey?"Не выявленные РЭС, подлежащие плановому контролю - " + countGrey:""};

        baseStationDto.setInfo(info);
        return baseStationDto;
    }

    public List<BaseStationDto> aggregateOld(List<BaseStation> baseStationList, Integer zoom) {
        List<BaseStationDto> baseStationDtoList = new LinkedList<>();

        for (BaseStation baseStation : baseStationList) {
            if (baseStationDtoList.isEmpty()) {
                baseStationDtoList.add(dtoService.getBaseStationDto(baseStation));
            } else {
                boolean aggregated = false;
                for (BaseStationDto baseStationDto : baseStationDtoList) {
                    if (zoom == 21 && distanceOld(baseStation, baseStationDto, "K") <= 0.005) {//change to lower distanceOld //0.0045011109739724245
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 20 && distanceOld(baseStation, baseStationDto, "K") <= 0.01) {//change to lower distanceOld //0.009400531899557338
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 19 && distanceOld(baseStation, baseStationDto, "K") <= 0.02) {//change to lower distanceOld //0.01632482771763881
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 18 && distanceOld(baseStation, baseStationDto, "K") <= 0.04) {//change to lower distanceOld //0.03223491895414472
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 17 && distanceOld(baseStation, baseStationDto, "K") <= 0.05) {//change to lower distanceOld //0.06856566195963213
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 16 && distanceOld(baseStation, baseStationDto, "K") <= 0.0625) {//change to lower distanceOld //0.0701160466652148
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 15 && distanceOld(baseStation, baseStationDto, "K") <= 0.125) {//change to lower distanceOld //0.1377618234489109
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 14 && distanceOld(baseStation, baseStationDto, "K") <= 0.25) {//0.20088793859573414 !
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 13 && distanceOld(baseStation, baseStationDto, "K") <= 0.6) {//change to higher distanceOld //0.5784220345040679
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 12 && distanceOld(baseStation, baseStationDto, "K") <= 1.4) {//change to higher distanceOld //1.1375660772017505
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 11 && distanceOld(baseStation, baseStationDto, "K") <= 3.4) {//change to higher distanceOld //2.2913646610699744
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 10 && distanceOld(baseStation, baseStationDto, "K") <= 4.8) {//change to higher distanceOld //4.542713975147452
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 9 && distanceOld(baseStation, baseStationDto, "K") <= 9.6) {//change to higher distanceOld //8.59961892760351
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 8 && distanceOld(baseStation, baseStationDto, "K") <= 20.2) {//change to higher distanceOld //17.843863090989773
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 7 && distanceOld(baseStation, baseStationDto, "K") <= 40.4) {//change to higher distanceOld //36.84453815479292
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else if (zoom == 6 && distanceOld(baseStation, baseStationDto, "K") <= 80.8) {//change to higher distance //71.29392669091827
                        aggregated = aggregateOld(baseStation, baseStationDto);
                    } else {
//                        if (distance(baseStation, baseStationDto, "K") > 0) {
//                            newBaseStationDto = dtoService.getBaseStationDto(baseStation);
//                        }
                    }



                }
                if (!aggregated) {
                    baseStationDtoList.add(dtoService.getBaseStationDto(baseStation));
                }
                //can be provide null pointer if added empty BaseStationDto.
//                if (newBaseStationDto.getLongitude() != null)
//                    baseStationDtoList.add(newBaseStationDto);
            }

        }

        return baseStationDtoList;
    }

    /**
     *
     * colors:
     * grey_yellow_green.png
     *
     * grey_yellow.png
     * grey_green.png
     * yellow_green.png
     *
     * grey.png
     * yellow.png
     * green.png
     *
     * @param baseStation
     * @param baseStationDto
     */
    private boolean aggregateOld(BaseStation baseStation, BaseStationDto baseStationDto) {
        //calculate point between points
        baseStationDto.setLatitude((baseStation.getLatitude() + baseStationDto.getLatitude()) / 2);
        baseStationDto.setLongitude((baseStation.getLongitude() + baseStationDto.getLongitude()) / 2);

        //get image color file
        if (!"grey_yellow_green.png".equals(baseStationDto.getImageName())) {
            if (!"grey_yellow.png".equals(baseStationDto.getImageName())
                    && !"grey_green.png".equals(baseStationDto.getImageName())
                    && !"yellow_green.png".equals(baseStationDto.getImageName())) {
                if ("grey.png".equals(baseStationDto.getImageName()) && "yellow.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow.png");
                } else if ("grey.png".equals(baseStationDto.getImageName()) && "green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_green.png");
                } else if ("yellow.png".equals(baseStationDto.getImageName()) && "green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("yellow_green.png");
                } else if ("yellow.png".equals(baseStationDto.getImageName()) && "grey.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow.png");
                } else if ("green.png".equals(baseStationDto.getImageName()) && "yellow.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("yellow_green.png");
                } else if ("green.png".equals(baseStationDto.getImageName()) && "grey.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_green.png");
                }
            } else if ("grey_yellow.png".equals(baseStationDto.getImageName())) {

                if ("grey_yellow.png".equals(baseStationDto.getImageName()) && "green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("grey_yellow.png".equals(baseStationDto.getImageName()) && "grey_green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("grey_yellow.png".equals(baseStationDto.getImageName()) && "yellow_green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                }
            } else if ("grey_green.png".equals(baseStationDto.getImageName())) {

                if ("grey_green.png".equals(baseStationDto.getImageName()) && "yellow.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("grey_green.png".equals(baseStationDto.getImageName()) && "yellow_green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("grey_green.png".equals(baseStationDto.getImageName()) && "grey_yellow.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                }
            } else if ("yellow_green.png".equals(baseStationDto.getImageName())) {

                if ("yellow_green.png".equals(baseStationDto.getImageName()) && "grey.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("yellow_green.png".equals(baseStationDto.getImageName()) && "grey_green.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                } else if ("yellow_green.png".equals(baseStationDto.getImageName()) && "grey_yellow.png".equals(baseStation.getIconName())) {
                    baseStationDto.setImageName("grey_yellow_green.png");
                }
            }
        }
        return true;
    }

    /**
     * @param unit "M" - Miles; "K" - Kilometers; "N" - Nautical Miles
     */
    private double distanceOld(BaseStation baseStation, BaseStationDto baseStationDto, String unit) {
        double lat1 = baseStation.getLatitude();
        double lon1 = baseStation.getLongitude();
        double lat2 = baseStationDto.getLatitude();
        double lon2 = baseStationDto.getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
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
    private double distance(BaseStation baseStation, LongLat longLat, String unit) {
        double lat1 = baseStation.getLatitude();
        double lon1 = baseStation.getLongitude();
        double lat2 = longLat.getLatitude();
        double lon2 = longLat.getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
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
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
