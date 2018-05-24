package com.simona.service.impl;

import com.simona.model.LongLat;
import com.simona.model.Station;
import com.simona.model.dto.ControlPointDTO;
import com.simona.model.dto.PointDTO;
import com.simona.model.dto.StationDTO;
import com.simona.service.AggregationStationsService;
import com.simona.service.DtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AggregationStationsServiceImpl implements AggregationStationsService {

    @Autowired
    private DtoService dtoService;

    @Override
    public List<PointDTO> aggregateStations(Double rightTopLatitude, Double rightTopLongitude,
                                            Double leftBottomLatitude, Double leftBottomLongitude,
                                            Set<StationDTO> stationDTOS, Integer zoom) {
        /* For check
        Integer count = 0;
        for (StationDTO stationDTO : stationDTOS) {
            for (ControlPointDTO controlPointDTO : stationDTO.getControlPoints()) {
                if (controlPointDTO.getStatus() != null && controlPointDTO.getStatus().equals(1)) {
                    count = count + 1;
                }
            }
        }
        log.info("Zoom : " + zoom);
        log.info("Count control point with status = 1 (before aggregation) : " + count);


        Integer shouldCount = 0;
        */

        Map<LongLat, Set> aggrMap = new HashMap<>();
        for (StationDTO station : stationDTOS) {
            if (leftBottomLatitude < station.getLatitude() && station.getLatitude() < rightTopLatitude
                    && leftBottomLongitude < station.getLongitude() && station.getLongitude() < rightTopLongitude) {

                /* For check
                for (ControlPointDTO controlPointDTO : station.getControlPoints()) {
                    if (controlPointDTO.getStatus() != null && controlPointDTO.getStatus().equals(1)) {
                        shouldCount = shouldCount + 1;
                    }
                }
                 */

                if (aggrMap.isEmpty()) {
                    addToAggrMap(aggrMap, station);
                } else {
                    boolean aggregated = false;

                    for (LongLat longLat : aggrMap.keySet()) {
                        if (zoom == 21 && AggregationHelper.distance(station, longLat, "K") <= 0.005) {//change to lower distance //0.0045011109739724245
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 20 && AggregationHelper.distance(station, longLat, "K") <= 0.01) {//change to lower distance //0.009400531899557338
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 19 && AggregationHelper.distance(station, longLat, "K") <= 0.02) {//change to lower distance //0.01632482771763881
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 18 && AggregationHelper.distance(station, longLat, "K") <= 0.04) {//change to lower distance //0.03223491895414472
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 17 && AggregationHelper.distance(station, longLat, "K") <= 0.05) {//change to lower distance //0.06856566195963213
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 16 && AggregationHelper.distance(station, longLat, "K") <= 0.07) {//change to lower distance //0.0701160466652148
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 15 && AggregationHelper.distance(station, longLat, "K") <= 0.15) {//change to lower distance //0.1377618234489109
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 14 && AggregationHelper.distance(station, longLat, "K") <= 0.201) {//0.20088793859573414 !
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 13 && AggregationHelper.distance(station, longLat, "K") <= 0.6) {//change to higher distance //0.5784220345040679
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 12 && AggregationHelper.distance(station, longLat, "K") <= 1.2) {//change to higher distance //1.1375660772017505
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 11 && AggregationHelper.distance(station, longLat, "K") <= 2.0) {//change to higher distance //2.2913646610699744
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 10 && AggregationHelper.distance(station, longLat, "K") <= 4.6) {//change to higher distance //4.542713975147452
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 9 && AggregationHelper.distance(station, longLat, "K") <= 8.0) {//change to higher distance //8.59961892760351
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 8 && AggregationHelper.distance(station, longLat, "K") <= 18.0) {//change to higher distance //17.843863090989773
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 7 && AggregationHelper.distance(station, longLat, "K") <= 37.0) {//change to higher distance //36.84453815479292
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        } else if (zoom == 6 && AggregationHelper.distance(station, longLat, "K") <= 72.0) {//change to higher distance //71.29392669091827
                            aggrMap.get(longLat).add(station);
                            aggregated = true;
                            break;
                        }
                    }

                    if (!aggregated) {
                        addToAggrMap(aggrMap, station);
                    }
                }
            }
        }
        /* For check
        log.info("Count control point with status = 1 (Should be) : " + shouldCount);
        Integer countYelow = 0;
        for (LongLat longLat : aggrMap.keySet()) {
            Set<StationDTO> stationListDTO = aggrMap.get(longLat);
            for (StationDTO stationDTO : stationListDTO) {
                for (ControlPointDTO controlPointDTO : stationDTO.getControlPoints()) {
                    if (controlPointDTO.getStatus() != null && controlPointDTO.getStatus().equals(1)) {
                        countYelow = countYelow + 1;
                    }
                }
            }
        }

        log.info("Count control point with status = 1 (after aggregation) : " + countYelow);
        */

        List<PointDTO> pointDTOS = new LinkedList<>();
        for (LongLat longLat : aggrMap.keySet()) {
            Set<StationDTO> stationListDTO = aggrMap.get(longLat);
            pointDTOS.add(createPointDTOFromStationDTOs(stationListDTO, longLat));
        }

        return pointDTOS;
    }

    private PointDTO createPointDTOFromStationDTOs(Set<StationDTO> stationListDTO, LongLat longLat) {
        PointDTO pointDTO = new PointDTO();

//        LongLat longLat = new LongLat(); todo for calculate average longLat
        String info = "";
        boolean grey = false;
        boolean green = false;
        boolean yellow = false;

        int sizeControlPoint = 0;
        if (stationListDTO.size() == 1) {
            for (StationDTO stationDTO : stationListDTO) {
                sizeControlPoint = sizeControlPoint + stationDTO.getControlPoints().size();
            }
        }

        if (stationListDTO.size() == 1 && sizeControlPoint == 1) {
            pointDTO = dtoService.getPointDto(stationListDTO);
        } else {
            boolean selected = false;
            for (StationDTO stationDTO : stationListDTO) {
                //calculate long lat todo for calculate average longLat
//            if (longLat.getLongitude() == null) { todo for calculate average longLat
//                longLat.setLatitude(station.getLatitude()); todo for calculate average longLat
//                longLat.setLongitude(station.getLongitude()); todo for calculate average longLat
//            } else {
//                longLat.setLatitude(longLat.getLatitude() + station.getLatitude()); todo for calculate average longLat
//                longLat.setLongitude(longLat.getLongitude() + station.getLongitude()); todo for calculate average longLat
//            }
                for (ControlPointDTO controlPointDTO : stationDTO.getControlPoints()) {
                    if (controlPointDTO.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
                        grey = true;
                        info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} " + "[" + controlPointDTO.getId() + "] " + stationDTO.getNick_name() + " </b></p>";
                    } else if (controlPointDTO.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
                        yellow = true;
                        info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + "[" + controlPointDTO.getId() + "] "  + stationDTO.getNick_name() + " </b></p>";
                    } else if (controlPointDTO.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
                        green = true;
                        info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + "[" + controlPointDTO.getId() + "] "  + stationDTO.getNick_name() + " </b></p>";
                    }
                }

                if (stationDTO.getUpdated() != null && LocalDateTime.now().minusMinutes(1).isBefore(stationDTO.getUpdated())) {
                    selected = true;
                } else {
                    stationDTO.setUpdated(null);
                }
            }

            pointDTO.setInfo(info);
            if (grey && green && yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_grey_yellow_green.png");
                } else {
                    pointDTO.setImageName("grey_yellow_green.png");
                }
            } else if (grey && green && !yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_grey_green.png");
                } else {
                    pointDTO.setImageName("grey_green.png");
                }
            } else if (grey && !green && yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_grey_yellow.png");
                } else {
                    pointDTO.setImageName("grey_yellow.png");
                }
            } else if (!grey && green && yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_yellow_green.png");
                } else {
                    pointDTO.setImageName("yellow_green.png");
                }
            } else if (grey && !green && !yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_grey.png");
                } else {
                    pointDTO.setImageName("grey.png");
                }
            } else if (!grey && !green && yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_yellow.png");
                } else {
                    pointDTO.setImageName("yellow.png");
                }
            } else if (!grey && green && !yellow) {
                if (selected) {
                    pointDTO.setImageName("selected_green.png");
                } else {
                    pointDTO.setImageName("green.png");
                }
            }

//        pointDTO.setLatitude(longLat.getLatitude()/stationList.size()); todo for calculate average longLat
//        pointDTO.setLongitude(longLat.getLongitude()/stationList.size()); todo for calculate average longLat
            pointDTO.setLatitude(longLat.getLatitude());
            pointDTO.setLongitude(longLat.getLongitude());
        }
        return pointDTO;
    }

    private void addToAggrMap(Map<LongLat, Set> myMap, StationDTO station) {
        LongLat longLat = new LongLat();
        longLat.setLatitude(station.getLatitude());
        longLat.setLongitude(station.getLongitude());

        if (myMap.get(longLat) != null) {
            myMap.get(longLat).add(station);
        } else {
            Set<StationDTO> stations = new TreeSet<>();
            stations.add(station);
            myMap.put(longLat, stations);
        }
    }
}
