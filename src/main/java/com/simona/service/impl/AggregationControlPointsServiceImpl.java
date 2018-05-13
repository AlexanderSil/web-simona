//package com.simona.service.impl;
//
//import com.simona.model.ControlPoint;
//import com.simona.model.LongLat;
//import com.simona.model.dto.PointDTO;
//import com.simona.service.AggregationControlPointsService;
//import com.simona.service.DtoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class AggregationControlPointsServiceImpl implements AggregationControlPointsService {
//
//    @Autowired
//    private DtoService dtoService;
//
//    @Override
//    public List<PointDTO> aggregateControlPoints(Double rightTopLatitude, Double rightTopLongitude,
//                                                 Double leftBottomLatitude, Double leftBottomLongitude,
//                                                 Iterable<ControlPoint> controlPointList, Integer zoom) {
//        Map<LongLat, List> aggrMap = new HashMap<>();
//        for (ControlPoint controlPoint : controlPointList) {
//            if (leftBottomLatitude < controlPoint.getStation().getLatitude() && controlPoint.getStation().getLatitude() < rightTopLatitude
//                    && leftBottomLongitude < controlPoint.getStation().getLongitude() && controlPoint.getStation().getLongitude() < rightTopLongitude) {
//                if (aggrMap.isEmpty()) {
//                    addToAggrMap(aggrMap, controlPoint);
//                } else {
//                    boolean aggregated = false;
//                    for (LongLat longLat : aggrMap.keySet()) {
//                        if (zoom == 21 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.005) {//change to lower distance //0.0045011109739724245
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 20 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.01) {//change to lower distance //0.009400531899557338
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 19 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.02) {//change to lower distance //0.01632482771763881
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 18 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.04) {//change to lower distance //0.03223491895414472
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 17 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.07) {//change to lower distance //0.06856566195963213
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 16 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.12) {//change to lower distance //0.0701160466652148
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 15 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.22) {//change to lower distance //0.1377618234489109
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 14 && AggregationHelper.distance(controlPoint, longLat, "K") <= 0.45) {//0.20088793859573414 !
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 13 && AggregationHelper.distance(controlPoint, longLat, "K") <= 1.0) {//change to higher distance //0.5784220345040679
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 12 && AggregationHelper.distance(controlPoint, longLat, "K") <= 2.5) {//change to higher distance //1.1375660772017505
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 11 && AggregationHelper.distance(controlPoint, longLat, "K") <= 3.9) {//change to higher distance //2.2913646610699744
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 10 && AggregationHelper.distance(controlPoint, longLat, "K") <= 7.5) {//change to higher distance //4.542713975147452
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 9 && AggregationHelper.distance(controlPoint, longLat, "K") <= 13.0) {//change to higher distance //8.59961892760351
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 8 && AggregationHelper.distance(controlPoint, longLat, "K") <= 24.0) {//change to higher distance //17.843863090989773
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 7 && AggregationHelper.distance(controlPoint, longLat, "K") <= 48.0) {//change to higher distance //36.84453815479292
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        } else if (zoom == 6 && AggregationHelper.distance(controlPoint, longLat, "K") <= 90.0) {//change to higher distance //71.29392669091827
//                            aggrMap.get(longLat).add(controlPoint);
//                            aggregated = true;
//                        }
//                    }
//                    if (!aggregated) {
//                        addToAggrMap(aggrMap, controlPoint);
//                    }
//                }
//            }
//        }
//
//        List<PointDTO> pointDTOS = new LinkedList<>();
//        for (LongLat longLat : aggrMap.keySet()) {
//            List<ControlPoint> list = aggrMap.get(longLat);
//            if (list.size() > 1) {
//                pointDTOS.add(aggregateControlPoint(list));
//            } else {
//                PointDTO pointDTO = dtoService.getPointDto(list.get(0));
//                pointDTOS.add(pointDTO);
//            }
//        }
//
//
//        return pointDTOS;
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
////        if (controlPoints.size() == 1) {
////            ControlPoint controlPoint = controlPoints.get(0);
////            if (controlPoint.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
////                grey = true;
////                info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ controlPoint.getStation().getNick_name() +" </b></p>";
////            } else if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
////                yellow = true;
////                info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
////            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
////                green = true;
////                info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
////            }
////
////            pointDTO.setLatitude(controlPoint.getStation().getLatitude());
////            pointDTO.setLongitude(controlPoint.getStation().getLongitude());
////
////            if (grey) {
////                pointDTO.setImageName("greyTriangle.png");
////            } else if (yellow) {
////                pointDTO.setImageName("yellowTriangle.png");
////            } else if (green) {
////                pointDTO.setImageName("greenTriangle.png");
////            }
//
////        } else {
//            for (ControlPoint controlPoint : controlPoints) {
//                if (longLat.getLongitude() == null) {
//                    longLat.setLatitude(controlPoint.getStation().getLatitude());
//                    longLat.setLongitude(controlPoint.getStation().getLongitude());
//                } else {
//                    longLat.setLatitude(longLat.getLatitude() + controlPoint.getStation().getLatitude());
//                    longLat.setLongitude(longLat.getLongitude() + controlPoint.getStation().getLongitude());
//                }
//
//                if (controlPoint.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
//                    grey = true;
//                    info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ controlPoint.getStation().getNick_name() +" </b></p>";
//                } else if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
//                    yellow = true;
//
//                    info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
//                } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
//                    green = true;
//                    info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
//                }
//
//            }
//            pointDTO.setLatitude(longLat.getLatitude()/controlPoints.size());
//            pointDTO.setLongitude(longLat.getLongitude()/controlPoints.size());
//
//            if (grey && green && yellow) {
//                pointDTO.setImageName("grey_yellow_green.png");
//            } else if (grey && green && !yellow) {
//                pointDTO.setImageName("grey_green.png");
//            } else if (grey && !green && yellow) {
//                pointDTO.setImageName("grey_yellow.png");
//            } else if (!grey && green && yellow) {
//                pointDTO.setImageName("yellow_green.png");
//            } else if (grey && !green && !yellow) {
//                pointDTO.setImageName("grey.png");
//            } else if (!grey && !green && yellow) {
//                pointDTO.setImageName("yellow.png");
//            } else if (!grey && green && !yellow) {
//                pointDTO.setImageName("green.png");
//            }
////        }
//
//        pointDTO.setInfo(info);
//        return pointDTO;
//    }
//
//    private void addToAggrMap(Map<LongLat, List> myMap, ControlPoint controlPoint) {
//        LongLat longLat = new LongLat();
//        longLat.setLatitude(controlPoint.getStation().getLatitude());
//        longLat.setLongitude(controlPoint.getStation().getLongitude());
//        List<ControlPoint> controlPoints = new LinkedList<>();
//        controlPoints.add(controlPoint);
//        myMap.put(longLat, controlPoints);
//    }
//
//}
