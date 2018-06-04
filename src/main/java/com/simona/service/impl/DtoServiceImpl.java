package com.simona.service.impl;

import com.simona.model.*;
import com.simona.model.dto.*;
import com.simona.service.DaoService;
import com.simona.service.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class DtoServiceImpl implements DtoService {

    @Autowired
    private DaoService daoService;

    @Override
    public Set<StationDTO> getStationDTOs(Iterable<Station> stations) {
        Set<StationDTO> stationDTOs = new TreeSet<>();

        for (Station station : stations) {
            StationDTO stationDTO = new StationDTO();

            stationDTO.setId(station.getId());

            List<ControlPointDTO> controlPoints = new LinkedList<>();
            for (ControlPoint controlPoint : station.getControlPoints()) {
                ControlPointDTO controlPointDTO = new ControlPointDTO();
                controlPointDTO.setId(controlPoint.getId());
//                controlPointDTO.setStation()
                controlPointDTO.setStn_sys_id(controlPoint.getStn_sys_id());
                controlPointDTO.setFreq(controlPoint.getFreq());
                controlPointDTO.setStatus(controlPoint.getStatus());
                controlPointDTO.setStn_sys_id(controlPoint.getStn_sys_id());
                controlPoints.add(controlPointDTO);
            }
            stationDTO.setControlPoints(controlPoints);

            stationDTO.setSys_id(station.getSys_id());

            StationRserviceDTO stationRserviceDTO = new StationRserviceDTO();
            stationRserviceDTO.setId(station.getRservice().getId());
//            stationRserviceDTO.setStation();
            stationRserviceDTO.setName(station.getRservice().getName());
            stationRserviceDTO.setSys_id(station.getRservice().getSys_id());

            RserviceTypesDTO rserviceTypesDTO = new RserviceTypesDTO();
            rserviceTypesDTO.setId(station.getRservice().getRserviceTypes().getId());
            rserviceTypesDTO.setName(station.getRservice().getRserviceTypes().getName());
            rserviceTypesDTO.setType(station.getRservice().getRserviceTypes().getType());

            stationRserviceDTO.setRserviceTypesDTO(rserviceTypesDTO);
            stationDTO.setRservice(stationRserviceDTO);

            stationDTO.setRserviceName(station.getRservice().getName());
            stationDTO.setRsrvc_sys_id(station.getRsrvc_sys_id());
            stationDTO.setEntrp_id(station.getEntrp_id());
            stationDTO.setEntrp_sys_id(station.getEntrp_sys_id());
            stationDTO.setLatitude(station.getLatitude());
            stationDTO.setLongitude(station.getLongitude());
            stationDTO.setRegion(station.getRegion());
            stationDTO.setCity(station.getCity());
            stationDTO.setAddress(station.getAddress());
            stationDTO.setNick_name(station.getNick_name());
            stationDTO.setNick_name_index(station.getNick_name_index());
            stationDTO.setStatus(station.getStatus());
            stationDTO.setPerm_number(station.getPerm_number());
            stationDTO.setPerm_date_from(station.getPerm_date_from());
            stationDTO.setPerm_date_to(station.getPerm_date_to());
            stationDTO.setPerm_remark(station.getPerm_remark());

            stationDTOs.add(stationDTO);
        }
        return stationDTOs;
    }

    @Override
    public List<PostDTO> getPostDTOs(Iterable<Post> posts) {
        List<PostDTO> postDTOS = new LinkedList<>();

        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setState(post.getState());
            postDTO.setLast_packet(post.getLast_packet());

            PostTracesDTO postTracesDTO = null;
            for (PostTraces postTraces : post.getPostTraces()) {
                if (postTracesDTO == null) {
                    postTracesDTO = new PostTracesDTO();
                    postTracesDTO.setId(postTraces.getId());
                    postTracesDTO.setTimestamp(postTraces.getTimestamp());
                    postTracesDTO.setLatitude(postTraces.getLatitude());
                    postTracesDTO.setLongitude(postTraces.getLongitude());
                    postTracesDTO.setSpeed(postTraces.getSpeed());
                    postTracesDTO.setDirection(postTraces.getDirection());
                } else {
                    if (postTraces.getTimestamp().isAfter(postTracesDTO.getTimestamp())) {
                        postTracesDTO.setId(postTraces.getId());
                        postTracesDTO.setTimestamp(postTraces.getTimestamp());
                        if (postTraces.getLatitude() != null && postTraces.getLatitude() != 0) {
                            postTracesDTO.setLatitude(postTraces.getLatitude());
                        }
                        if (postTraces.getLongitude() != null && postTraces.getLongitude() != 0) {
                            postTracesDTO.setLongitude(postTraces.getLongitude());
                        }
                        postTracesDTO.setSpeed(postTraces.getSpeed());
                        if (postTraces.getDirection() != null && postTraces.getDirection() != 0) {
                            postTracesDTO.setDirection(postTraces.getDirection());
                        }
                    }
                }
            }

//            if (postTracesDTO != null) {
//                postDTO.setLastPostTraces(postTracesDTO);
//
//                postDTO.setImageName(getImageNameForPost(postTracesDTO.getDirection()));
//                postDTO.setInfo("Пост ID: " + post.getId()
//                        + (postTracesDTO.getSpeed() != null ? ". Скорость: " + postTracesDTO.getSpeed().toString() + "km/h" : "")
//                        + ". Long: " + postTracesDTO.getLongitude() + "; Lat: " + postTracesDTO.getLatitude());
//            } else {
//                postDTO.setLastPostTraces(new PostTracesDTO());
//            }
//            postDTOS.add(postDTO);


            if (postTracesDTO != null) {
                postDTO.setLastPostTraces(postTracesDTO);

            } else {
                postTracesDTO = new PostTracesDTO();
                postTracesDTO.setId(1);
                postTracesDTO.setTimestamp(LocalDateTime.now());
                postTracesDTO.setLatitude(0.1);
                postTracesDTO.setLongitude(0.1);
                postTracesDTO.setSpeed(0);
                postTracesDTO.setDirection(0.0);
                postDTO.setLastPostTraces(postTracesDTO);
            }
            postDTO.setImageName(getImageNameForPost(postTracesDTO.getDirection(), postTracesDTO.getId()));
            postDTO.setInfo("Пост ID: " + post.getId()
                    + (postTracesDTO.getSpeed() != null ? ". Скорость: " + postTracesDTO.getSpeed().toString() + "km/h" : "")
                    + ". Long: " + postTracesDTO.getLongitude() + "; Lat: " + postTracesDTO.getLatitude());
            postDTOS.add(postDTO);

        }


        return postDTOS;
    }

    @Override
    public List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices, Set<StationDTO> stationDTOS) {
        List<RserviceDTO> rserviceDTOs = new LinkedList<>();

        Integer size = 0;
        Integer detect = 0;
        Integer measurement = 0;
        Integer unidentifiedStations = 0;
        for (StationDTO stationDTO : stationDTOS) {
            size = size + stationDTO.getControlPoints().size();

            for (ControlPointDTO controlPoint : stationDTO.getControlPoints()) {
                if (controlPoint.getStatus() != null) {
                    if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено, DETECT)
                        detect = detect + 1;
                    } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено, MEASUREMENT)
                        measurement = measurement +1;
                    }
                }
                if (controlPoint.getStn_sys_id() != null) {
                    if (controlPoint.getStn_sys_id() == 0) {// неидентифицированная станция
                        unidentifiedStations = unidentifiedStations + 1;
                    }
                }
            }
        }
        RserviceDTO rserviceDTO = new RserviceDTO();
        rserviceDTO.setId(0);
        rserviceDTO.setName("Всего:");
        rserviceDTO.setCount(size - unidentifiedStations);
        if (unidentifiedStations > 0) {
            rserviceDTO.setUnidentifiedcount(unidentifiedStations);
        }
        rserviceDTO.setDetected("Обнаружено:");
        rserviceDTO.setDetectedcount(detect);
        rserviceDTO.setMeasured("Измерено:");
        rserviceDTO.setMeasuredcount(measurement);

        rserviceDTOs.add(rserviceDTO);

        for (Rservice rservice : rservices) {
            if (rservice.getStations().size() >= 1) {
                rserviceDTO = new RserviceDTO();
                rserviceDTO.setId(rservice.getRserviceTypes().getId());
                rserviceDTO.setName(rservice.getRserviceTypes().getName());
                rserviceDTO.setDetected("Обнаружено:");
                rserviceDTO.setMeasured("Измерено:");
                unidentifiedStations = 0;
                for (Station station: rservice.getStations()) {
                    for (ControlPoint controlPoint : station.getControlPoints()) {
                        if (controlPoint.getStatus() != null) {
                            if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено, DETECT)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
                            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено, MEASUREMENT)
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + 1);
                            }
                        }
                        if (controlPoint.getStn_sys_id() != null) {
                            if (controlPoint.getStn_sys_id() == 0) {// неидентифицированная станция
                                unidentifiedStations = unidentifiedStations + 1;
                            }
                        }
                    }
                    rserviceDTO.setCount(rserviceDTO.getCount() + station.getControlPoints().size());
                }
                if (unidentifiedStations > 0) {
                    rserviceDTO.setUnidentifiedcount(unidentifiedStations);
                }

                rserviceDTOs.add(rserviceDTO);
            }
        }
        return rserviceDTOs;
    }

    @Override
    public PostDTOTemp getPostDTOTemp(PostDTO post) {
        PostDTOTemp postDTOTemp = new PostDTOTemp();
        postDTOTemp.setId(post.getId());
        postDTOTemp.setState(post.getState());
        if (post.getState() == null || post.getState() == 1) {
            postDTOTemp.setIconName("blackCar.png");
        }
        if (post.getState() != null && post.getState() == 0) {
            postDTOTemp.setIconName("greenCar.png");
        }
        postDTOTemp.setName("РМ-1500-Р3/5М");//todo hardcode
        return postDTOTemp;
    }

    @Override
    public List<RegionDTO> getRegionDTOs(List<Region> regionList) {
        List<RegionDTO> regionDTOS = new LinkedList<>();
        if (regionList != null && !regionList.isEmpty()) {
            for (Region region : regionList) {
                RegionDTO regionDTO = new RegionDTO();

                regionDTO.setId(region.getId());

                regionDTO.setName(region.getRegionName());

                regionDTO.setLatitudeX(region.getLatitudeX());
                regionDTO.setLongitudeX(region.getLongitudeX());

                regionDTO.setLatitudeY(region.getLatitudeY());
                regionDTO.setLongitudeY(region.getLongitudeY());

                regionDTOS.add(regionDTO);
            }
        }
        return regionDTOS;
    }

    public PointDTO getPointDto(ControlPoint controlPoint) {
        PointDTO pointDTO = new PointDTO();

        pointDTO.setLongitude(controlPoint.getStation().getLongitude());
        pointDTO.setLatitude(controlPoint.getStation().getLatitude());

        String info = "";
        if (controlPoint.getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
            pointDTO.setImageName("greyTriangle.png");
            info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ controlPoint.getStation().getNick_name() +" </b></p>";
        } else if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
            pointDTO.setImageName("yellowTriangle.png");
            info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
        } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
            pointDTO.setImageName("greenTriangle.png");
            info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + controlPoint.getStation().getNick_name() +" </b></p>";
        }

        pointDTO.setInfo(info);
        return pointDTO;
    }


    public PointDTO getPointDto(Set<StationDTO> stationDTOs) {
        PointDTO pointDTO = new PointDTO();

        for (StationDTO stationDTO : stationDTOs) {
            pointDTO.setLongitude(stationDTO.getLongitude());
            pointDTO.setLatitude(stationDTO.getLatitude());

            String info = "";
            if (stationDTO.getControlPoints().get(0).getStatus() == null) {// серого цвета - РЭС, подлежащая контролю (не выявлена, не измерена)
                if (stationDTO.getUpdated() != null && stationDTO.getUpdated().isBefore(LocalDateTime.now().minusMinutes(1))) {
                    pointDTO.setImageName("greyTriangleSelected.png");
                } else {
                    pointDTO.setImageName("greyTriangle.png");
                    stationDTO.setUpdated(null);
                }
                info = info + "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ stationDTO.getNick_name() +" </b></p>";
            } else if (stationDTO.getControlPoints().get(0).getStatus() == 1) {// желтого цвета – РЭС выявлена (не измерена)
                if (stationDTO.getUpdated() != null && stationDTO.getUpdated().isBefore(LocalDateTime.now().minusMinutes(1))) {
                    pointDTO.setImageName("yellowTriangleSelected.png");
                } else {
                    pointDTO.setImageName("yellowTriangle.png");
                    stationDTO.setUpdated(null);
                }
                info = info + "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} " + stationDTO.getNick_name() +" </b></p>";
            } else if (stationDTO.getControlPoints().get(0).getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена
                if (stationDTO.getUpdated() != null && stationDTO.getUpdated().isBefore(LocalDateTime.now().minusMinutes(1))) {
                    pointDTO.setImageName("greenTriangleSelected.png");
                } else {
                    pointDTO.setImageName("greenTriangle.png");
                    stationDTO.setUpdated(null);
                }
                info = info + "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} " + stationDTO.getNick_name() +" </b></p>";
            }

            pointDTO.setInfo(info);
        }

        return pointDTO;
    }

    @Override
    public String getImageNameForPost(Double direction, Integer postID) {
        String onlineOffline = "offline";
        if (!daoService.getMarker()) {
            List<PostDTO> postDTOs = daoService.getPostDTOs();

            for (PostDTO postDTO : postDTOs) {
                if (postDTO.getId().equals(postID)) {
                    if (postDTO.getState() == null) {
                        onlineOffline = "offline";
                    } else if (postDTO.getState().equals(0)) {//"ONLINE"
                        onlineOffline = "online";
                    } else if (postDTO.getState().equals(1)){//"OFFLINE"
                        onlineOffline = "offline";
                    }
                }
            }
        }

        if (direction != null) {
            if (357.5d <= direction || direction < 182.5d) {
                if (direction < 2.5d) {
                    return "pointer/" + onlineOffline + "/0.png";
                }
                if (357.5d <= direction) {
                    return "pointer/" + onlineOffline + "/0.png";
                } else if (2.5d <= direction && direction < 7.5d) {
                    return "pointer/" + onlineOffline + "/5.png";
                } else if (7.5d <= direction && direction < 12.5d) {
                    return "pointer/" + onlineOffline + "/10.png";
                } else if (12.5d <= direction && direction < 17.5d) {
                    return "pointer/" + onlineOffline + "/15.png";
                } else if (17.5d <= direction && direction < 22.5d) {
                    return "pointer/" + onlineOffline + "/20.png";
                } else if (22.5d <= direction && direction < 27.5d) {
                    return "pointer/" + onlineOffline + "/25.png";
                } else if (27.5d <= direction && direction < 32.5d) {
                    return "pointer/" + onlineOffline + "/30.png";
                } else if (32.5d <= direction && direction < 37.5d) {
                    return "pointer/" + onlineOffline + "/35.png";
                } else if (37.5d <= direction && direction < 42.5d) {
                    return "pointer/" + onlineOffline + "/40.png";
                } else if (42.5d <= direction && direction < 47.5d) {
                    return "pointer/" + onlineOffline + "/45.png";
                } else if (47.5d <= direction && direction < 52.5d) {
                    return "pointer/" + onlineOffline + "/50.png";
                } else if (52.5d <= direction && direction < 57.5d) {
                    return "pointer/" + onlineOffline + "/55.png";
                } else if (57.5d <= direction && direction < 62.5d) {
                    return "pointer/" + onlineOffline + "/60.png";
                } else if (62.5d <= direction && direction < 67.5d) {
                    return "pointer/" + onlineOffline + "/65.png";
                } else if (67.5d <= direction && direction < 72.5d) {
                    return "pointer/" + onlineOffline + "/70.png";
                } else if (72.5d <= direction && direction < 77.5d) {
                    return "pointer/" + onlineOffline + "/75.png";
                } else if (77.5d <= direction && direction < 82.5d) {
                    return "pointer/" + onlineOffline + "/80.png";
                } else if (82.5d <= direction && direction < 87.5d) {
                    return "pointer/" + onlineOffline + "/85.png";
                } else if (87.5d <= direction && direction < 92.5d) {
                    return "pointer/" + onlineOffline + "/90.png";
                } else if (92.5d <= direction && direction < 97.5d) {
                    return "pointer/" + onlineOffline + "/95.png";
                } else if (97.5d <= direction && direction < 102.5d) {
                    return "pointer/" + onlineOffline + "/100.png";
                } else if (102.5d <= direction && direction < 107.5d) {
                    return "pointer/" + onlineOffline + "/105.png";
                } else if (107.5d <= direction && direction < 112.5d) {
                    return "pointer/" + onlineOffline + "/110.png";
                } else if (112.5d <= direction && direction < 117.5d) {
                    return "pointer/" + onlineOffline + "/115.png";
                } else if (117.5d <= direction && direction < 122.5d) {
                    return "pointer/" + onlineOffline + "/120.png";
                } else if (122.5d <= direction && direction < 127.5d) {
                    return "pointer/" + onlineOffline + "/125.png";
                } else if (127.5d <= direction && direction < 132.5d) {
                    return "pointer/" + onlineOffline + "/130.png";
                } else if (132.5d <= direction && direction < 137.5d) {
                    return "pointer/" + onlineOffline + "/135.png";
                } else if (137.5d <= direction && direction < 142.5d) {
                    return "pointer/" + onlineOffline + "/140.png";
                } else if (142.5d <= direction && direction < 147.5d) {
                    return "pointer/" + onlineOffline + "/145.png";
                } else if (147.5d <= direction && direction < 152.5d) {
                    return "pointer/" + onlineOffline + "/150.png";
                } else if (152.5d <= direction && direction < 157.5d) {
                    return "pointer/" + onlineOffline + "/155.png";
                } else if (157.5d <= direction && direction < 162.5d) {
                    return "pointer/" + onlineOffline + "/160.png";
                } else if (162.5d <= direction && direction < 167.5d) {
                    return "pointer/" + onlineOffline + "/165.png";
                } else if (167.5d <= direction && direction < 172.5d) {
                    return "pointer/" + onlineOffline + "/170.png";
                } else if (172.5d <= direction && direction < 177.5d) {
                    return "pointer/" + onlineOffline + "/175.png";
                } else if (177.5d <= direction && direction < 182.5d) {
                    return "pointer/" + onlineOffline + "/180.png";
                }
            } else {
                if (182.5d <= direction && direction < 187.5d) {
                    return "pointer/" + onlineOffline + "/185.png";
                } else if (187.5d <= direction && direction < 192.5d) {
                    return "pointer/" + onlineOffline + "/190.png";
                } else if (192.5d <= direction && direction < 197.5d) {
                    return "pointer/" + onlineOffline + "/195.png";
                } else if (197.5d <= direction && direction < 202.5d) {
                    return "pointer/" + onlineOffline + "/200.png";
                } else if (202.5d <= direction && direction < 207.5d) {
                    return "pointer/" + onlineOffline + "/205.png";
                } else if (207.5d <= direction && direction < 212.5d) {
                    return "pointer/" + onlineOffline + "/210.png";
                } else if (212.5d <= direction && direction < 217.5d) {
                    return "pointer/" + onlineOffline + "/215.png";
                } else if (217.5d <= direction && direction < 222.5d) {
                    return "pointer/" + onlineOffline + "/220.png";
                } else if (222.5d <= direction && direction < 227.5d) {
                    return "pointer/" + onlineOffline + "/225.png";
                } else if (227.5d <= direction && direction < 232.5d) {
                    return "pointer/" + onlineOffline + "/230.png";
                } else if (232.5d <= direction && direction < 237.5d) {
                    return "pointer/" + onlineOffline + "/235.png";
                } else if (237.5d <= direction && direction < 242.5d) {
                    return "pointer/" + onlineOffline + "/240.png";
                } else if (242.5d <= direction && direction < 247.5d) {
                    return "pointer/" + onlineOffline + "/245.png";
                } else if (247.5d <= direction && direction < 252.5d) {
                    return "pointer/" + onlineOffline + "/250.png";
                } else if (252.5d <= direction && direction < 257.5d) {
                    return "pointer/" + onlineOffline + "/255.png";
                } else if (257.5d <= direction && direction < 262.5d) {
                    return "pointer/" + onlineOffline + "/260.png";
                } else if (262.5d <= direction && direction < 267.5d) {
                    return "pointer/" + onlineOffline + "/265.png";
                } else if (267.5d <= direction && direction < 272.5d) {
                    return "pointer/" + onlineOffline + "/270.png";
                } else if (272.5d <= direction && direction < 277.5d) {
                    return "pointer/" + onlineOffline + "/275.png";
                } else if (277.5d <= direction && direction < 282.5d) {
                    return "pointer/" + onlineOffline + "/280.png";
                } else if (282.5d <= direction && direction < 287.5d) {
                    return "pointer/" + onlineOffline + "/285.png";
                } else if (287.5d <= direction && direction < 292.5d) {
                    return "pointer/" + onlineOffline + "/290.png";
                } else if (292.5d <= direction && direction < 297.5d) {
                    return "pointer/" + onlineOffline + "/295.png";
                } else if (297.5d <= direction && direction < 302.5d) {
                    return "pointer/" + onlineOffline + "/300.png";
                } else if (302.5d <= direction && direction < 307.5d) {
                    return "pointer/" + onlineOffline + "/305.png";
                } else if (307.5d <= direction && direction < 312.5d) {
                    return "pointer/" + onlineOffline + "/310.png";
                } else if (312.5d <= direction && direction < 317.5d) {
                    return "pointer/" + onlineOffline + "/315.png";
                } else if (317.5d <= direction && direction < 322.5d) {
                    return "pointer/" + onlineOffline + "/320.png";
                } else if (322.5d <= direction && direction < 327.5d) {
                    return "pointer/" + onlineOffline + "/325.png";
                } else if (327.5d <= direction && direction < 332.5d) {
                    return "pointer/" + onlineOffline + "/330.png";
                } else if (332.5d <= direction && direction < 337.5d) {
                    return "pointer/" + onlineOffline + "/335.png";
                } else if (337.5d <= direction && direction < 342.5d) {
                    return "pointer/" + onlineOffline + "/340.png";
                } else if (342.5d <= direction && direction < 347.5d) {
                    return "pointer/" + onlineOffline + "/345.png";
                } else if (347.5d <= direction && direction < 352.5d) {
                    return "pointer/" + onlineOffline + "/350.png";
                } else if (352.5d <= direction && direction < 357.5d) {
                    return "pointer/" + onlineOffline + "/355.png";
                }
            }
        }
        return "pointer/" + onlineOffline + "/0.png";
    }


}
