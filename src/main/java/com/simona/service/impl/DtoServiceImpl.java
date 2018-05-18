package com.simona.service.impl;

import com.simona.model.*;
import com.simona.model.dto.*;
import com.simona.service.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class DtoServiceImpl implements DtoService {

    @Autowired
    private MenuServiceImpl monitoringService;

    @Override
    public List<StationDTO> getStationDTOs(Iterable<Station> stations) {
        List<StationDTO> stationDTOs = new LinkedList<>();

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
                controlPoints.add(controlPointDTO);
            }
            stationDTO.setControlPoints(controlPoints);

            stationDTO.setSys_id(station.getSys_id());

            StationRserviceDTO stationRserviceDTO = new StationRserviceDTO();
            stationRserviceDTO.setId(station.getRservice().getId());
//            stationRserviceDTO.setStation();
            stationRserviceDTO.setName(station.getRservice().getName());
            stationRserviceDTO.setSys_id(station.getRservice().getSys_id());
            stationRserviceDTO.setType(station.getRservice().getType());
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
    public List<PostDTO> getPostDTOs(Iterable<Post> posts, PostTraces postTraces) {
        List<PostDTO> postDTOS = new LinkedList<>();

        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setState(post.getState());
            postDTO.setLast_packet(post.getLast_packet());

//            List<PostTracesDTO> postTracesDTOS = new LinkedList<>();
//            postTracesDTOS.addAll(post.getPostTraces());
//            postDTO.setPostTraces(postTracesDTOS);

//            for (PostTraces postTraces : post.getPostTraces()) {//todo activate when fixed (postTraces.getTimestamp().equals(post.getLast_packet()))
//                if (postTraces.getId().equals(183187)) {
//
//                }
//                if (postTraces.getTimestamp().equals(post.getLast_packet())) {
                    PostTracesDTO postTracesDTO = new PostTracesDTO();
                    postTracesDTO.setId(postTraces.getId());
                    postTracesDTO.setTimestamp(postTraces.getTimestamp());
                    postTracesDTO.setLatitude(postTraces.getLatitude());
                    postTracesDTO.setLongitude(postTraces.getLongitude());
                    postTracesDTO.setSpeed(postTraces.getSpeed());
                    postTracesDTO.setDirection(postTraces.getDirection());

                    postDTO.setLastPostTraces(postTracesDTO);
//                }
//            }

            postDTO.setImageName(getImageNameForPost(postTraces.getDirection()));
            postDTO.setInfo("Пост ID: " + post.getId()
                    + (postTraces.getSpeed()!= null ? ". Скорость: " + postTraces.getSpeed().toString() + "km/h" : "" )
                    + ". Long: " + postTraces.getLongitude() + "; Lat: " + postTraces.getLatitude());

            postDTOS.add(postDTO);
        }


        return postDTOS;
    }

    @Override
    public List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices, List<ControlPoint> controlPoints) {
        List<RserviceDTO> rserviceDTOs = new LinkedList<>();

        RserviceDTO rserviceDTO = new RserviceDTO();
        rserviceDTO.setName("Всего:");
        rserviceDTO.setCount(controlPoints.size());

        Integer detect = 0;
        Integer measurement = 0;
        for (ControlPoint controlPoint : controlPoints) {
            if (controlPoint.getStatus() != null) {
                if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено, DETECT)
                    detect = detect + 1;
                } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено, MEASUREMENT)
                    measurement = measurement +1;
                }
            }
        }

        rserviceDTO.setDetected("Обнаружено:");
        rserviceDTO.setDetectedcount(detect);
        rserviceDTO.setMeasured("Измерено:");
        rserviceDTO.setMeasuredcount(measurement);

        rserviceDTOs.add(rserviceDTO);

        for (Rservice rservice : rservices) {
            if (rservice.getStations().size() >= 1) {
                rserviceDTO = new RserviceDTO();

                rserviceDTO.setName(rservice.getName());
                rserviceDTO.setDetected("Обнаружено:");
                rserviceDTO.setMeasured("Измерено:");

                for (Station station: rservice.getStations()) {
                    for (ControlPoint controlPoint : station.getControlPoints()) {
                        if (controlPoint.getStatus() != null) {
                            if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено, DETECT)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
                            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено, MEASUREMENT)
                                rserviceDTO.setMeasuredcount(rserviceDTO.getMeasuredcount() + 1);
                            }
                        }
                    }
                    rserviceDTO.setCount(rserviceDTO.getCount() + station.getControlPoints().size());
                }

                rserviceDTOs.add(rserviceDTO);
            }
        }
        return rserviceDTOs;
    }

    @Override
    public PostDTOTemp getPostDTO(Post post) {
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


    public PointDTO getPointDto(StationDTO stationDTO) {
        PointDTO pointDTO = new PointDTO();

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
        return pointDTO;
    }

    @Override
    public PointDTO getPointsDTOFromPosts(PostTraces postTraces) {

        if (postTraces != null) {
            PointDTO pointDTO = new PointDTO();

            pointDTO.setLongitude(postTraces.getLongitude());
            pointDTO.setLatitude(postTraces.getLatitude());

            if (postTraces.getDirection() != null) {
                if (357.5d <= postTraces.getDirection() || postTraces.getDirection() < 182.5d) {
                    if (postTraces.getDirection() < 2.5d) {
                        pointDTO.setImageName("pointer/0.png");
                    }
                    if (357.5d <= postTraces.getDirection()) {
                        pointDTO.setImageName("pointer/0.png");
                    } else if (2.5d <= postTraces.getDirection() && postTraces.getDirection() < 7.5d) {
                        pointDTO.setImageName("pointer/5.png");
                    } else if (7.5d <= postTraces.getDirection() && postTraces.getDirection() < 12.5d) {
                        pointDTO.setImageName("pointer/10.png");
                    } else if (12.5d <= postTraces.getDirection() && postTraces.getDirection() < 17.5d) {
                        pointDTO.setImageName("pointer/15.png");
                    } else if (17.5d <= postTraces.getDirection() && postTraces.getDirection() < 22.5d) {
                        pointDTO.setImageName("pointer/20.png");
                    } else if (22.5d <= postTraces.getDirection() && postTraces.getDirection() < 27.5d) {
                        pointDTO.setImageName("pointer/25.png");
                    } else if (27.5d <= postTraces.getDirection() && postTraces.getDirection() < 32.5d) {
                        pointDTO.setImageName("pointer/30.png");
                    } else if (32.5d <= postTraces.getDirection() && postTraces.getDirection() < 37.5d) {
                        pointDTO.setImageName("pointer/35.png");
                    } else if (37.5d <= postTraces.getDirection() && postTraces.getDirection() < 42.5d) {
                        pointDTO.setImageName("pointer/40.png");
                    } else if (42.5d <= postTraces.getDirection() && postTraces.getDirection() < 47.5d) {
                        pointDTO.setImageName("pointer/45.png");
                    } else if (47.5d <= postTraces.getDirection() && postTraces.getDirection() < 52.5d) {
                        pointDTO.setImageName("pointer/50.png");
                    } else if (52.5d <= postTraces.getDirection() && postTraces.getDirection() < 57.5d) {
                        pointDTO.setImageName("pointer/55.png");
                    } else if (57.5d <= postTraces.getDirection() && postTraces.getDirection() < 62.5d) {
                        pointDTO.setImageName("pointer/60.png");
                    } else if (62.5d <= postTraces.getDirection() && postTraces.getDirection() < 67.5d) {
                        pointDTO.setImageName("pointer/65.png");
                    } else if (67.5d <= postTraces.getDirection() && postTraces.getDirection() < 72.5d) {
                        pointDTO.setImageName("pointer/70.png");
                    } else if (72.5d <= postTraces.getDirection() && postTraces.getDirection() < 77.5d) {
                        pointDTO.setImageName("pointer/75.png");
                    } else if (77.5d <= postTraces.getDirection() && postTraces.getDirection() < 82.5d) {
                        pointDTO.setImageName("pointer/80.png");
                    } else if (82.5d <= postTraces.getDirection() && postTraces.getDirection() < 87.5d) {
                        pointDTO.setImageName("pointer/85.png");
                    } else if (87.5d <= postTraces.getDirection() && postTraces.getDirection() < 92.5d) {
                        pointDTO.setImageName("pointer/90.png");
                    } else if (92.5d <= postTraces.getDirection() && postTraces.getDirection() < 97.5d) {
                        pointDTO.setImageName("pointer/95.png");
                    } else if (97.5d <= postTraces.getDirection() && postTraces.getDirection() < 102.5d) {
                        pointDTO.setImageName("pointer/100.png");
                    } else if (102.5d <= postTraces.getDirection() && postTraces.getDirection() < 107.5d) {
                        pointDTO.setImageName("pointer/105.png");
                    } else if (107.5d <= postTraces.getDirection() && postTraces.getDirection() < 112.5d) {
                        pointDTO.setImageName("pointer/110.png");
                    } else if (112.5d <= postTraces.getDirection() && postTraces.getDirection() < 117.5d) {
                        pointDTO.setImageName("pointer/115.png");
                    } else if (117.5d <= postTraces.getDirection() && postTraces.getDirection() < 122.5d) {
                        pointDTO.setImageName("pointer/120.png");
                    } else if (122.5d <= postTraces.getDirection() && postTraces.getDirection() < 127.5d) {
                        pointDTO.setImageName("pointer/125.png");
                    } else if (127.5d <= postTraces.getDirection() && postTraces.getDirection() < 132.5d) {
                        pointDTO.setImageName("pointer/130.png");
                    } else if (132.5d <= postTraces.getDirection() && postTraces.getDirection() < 137.5d) {
                        pointDTO.setImageName("pointer/135.png");
                    } else if (137.5d <= postTraces.getDirection() && postTraces.getDirection() < 142.5d) {
                        pointDTO.setImageName("pointer/140.png");
                    } else if (142.5d <= postTraces.getDirection() && postTraces.getDirection() < 147.5d) {
                        pointDTO.setImageName("pointer/145.png");
                    } else if (147.5d <= postTraces.getDirection() && postTraces.getDirection() < 152.5d) {
                        pointDTO.setImageName("pointer/150.png");
                    } else if (152.5d <= postTraces.getDirection() && postTraces.getDirection() < 157.5d) {
                        pointDTO.setImageName("pointer/155.png");
                    } else if (157.5d <= postTraces.getDirection() && postTraces.getDirection() < 162.5d) {
                        pointDTO.setImageName("pointer/160.png");
                    } else if (162.5d <= postTraces.getDirection() && postTraces.getDirection() < 167.5d) {
                        pointDTO.setImageName("pointer/165.png");
                    } else if (167.5d <= postTraces.getDirection() && postTraces.getDirection() < 172.5d) {
                        pointDTO.setImageName("pointer/170.png");
                    } else if (172.5d <= postTraces.getDirection() && postTraces.getDirection() < 177.5d) {
                        pointDTO.setImageName("pointer/175.png");
                    } else if (177.5d <= postTraces.getDirection() && postTraces.getDirection() < 182.5d) {
                        pointDTO.setImageName("pointer/180.png");
                    }
                } else {
                    if (182.5d <= postTraces.getDirection() && postTraces.getDirection() < 187.5d) {
                        pointDTO.setImageName("pointer/185.png");
                    } else if (187.5d <= postTraces.getDirection() && postTraces.getDirection() < 192.5d) {
                        pointDTO.setImageName("pointer/190.png");
                    } else if (192.5d <= postTraces.getDirection() && postTraces.getDirection() < 197.5d) {
                        pointDTO.setImageName("pointer/195.png");
                    } else if (197.5d <= postTraces.getDirection() && postTraces.getDirection() < 202.5d) {
                        pointDTO.setImageName("pointer/200.png");
                    } else if (202.5d <= postTraces.getDirection() && postTraces.getDirection() < 207.5d) {
                        pointDTO.setImageName("pointer/205.png");
                    } else if (207.5d <= postTraces.getDirection() && postTraces.getDirection() < 212.5d) {
                        pointDTO.setImageName("pointer/210.png");
                    } else if (212.5d <= postTraces.getDirection() && postTraces.getDirection() < 217.5d) {
                        pointDTO.setImageName("pointer/215.png");
                    } else if (217.5d <= postTraces.getDirection() && postTraces.getDirection() < 222.5d) {
                        pointDTO.setImageName("pointer/220.png");
                    } else if (222.5d <= postTraces.getDirection() && postTraces.getDirection() < 227.5d) {
                        pointDTO.setImageName("pointer/225.png");
                    } else if (227.5d <= postTraces.getDirection() && postTraces.getDirection() < 232.5d) {
                        pointDTO.setImageName("pointer/230.png");
                    } else if (232.5d <= postTraces.getDirection() && postTraces.getDirection() < 237.5d) {
                        pointDTO.setImageName("pointer/235.png");
                    } else if (237.5d <= postTraces.getDirection() && postTraces.getDirection() < 242.5d) {
                        pointDTO.setImageName("pointer/240.png");
                    } else if (242.5d <= postTraces.getDirection() && postTraces.getDirection() < 247.5d) {
                        pointDTO.setImageName("pointer/245.png");
                    } else if (247.5d <= postTraces.getDirection() && postTraces.getDirection() < 252.5d) {
                        pointDTO.setImageName("pointer/250.png");
                    } else if (252.5d <= postTraces.getDirection() && postTraces.getDirection() < 257.5d) {
                        pointDTO.setImageName("pointer/255.png");
                    } else if (257.5d <= postTraces.getDirection() && postTraces.getDirection() < 262.5d) {
                        pointDTO.setImageName("pointer/260.png");
                    } else if (262.5d <= postTraces.getDirection() && postTraces.getDirection() < 267.5d) {
                        pointDTO.setImageName("pointer/265.png");
                    } else if (267.5d <= postTraces.getDirection() && postTraces.getDirection() < 272.5d) {
                        pointDTO.setImageName("pointer/270.png");
                    } else if (272.5d <= postTraces.getDirection() && postTraces.getDirection() < 277.5d) {
                        pointDTO.setImageName("pointer/275.png");
                    } else if (277.5d <= postTraces.getDirection() && postTraces.getDirection() < 282.5d) {
                        pointDTO.setImageName("pointer/280.png");
                    } else if (282.5d <= postTraces.getDirection() && postTraces.getDirection() < 287.5d) {
                        pointDTO.setImageName("pointer/285.png");
                    } else if (287.5d <= postTraces.getDirection() && postTraces.getDirection() < 292.5d) {
                        pointDTO.setImageName("pointer/290.png");
                    } else if (292.5d <= postTraces.getDirection() && postTraces.getDirection() < 297.5d) {
                        pointDTO.setImageName("pointer/295.png");
                    } else if (297.5d <= postTraces.getDirection() && postTraces.getDirection() < 302.5d) {
                        pointDTO.setImageName("pointer/300.png");
                    } else if (302.5d <= postTraces.getDirection() && postTraces.getDirection() < 307.5d) {
                        pointDTO.setImageName("pointer/305.png");
                    } else if (307.5d <= postTraces.getDirection() && postTraces.getDirection() < 312.5d) {
                        pointDTO.setImageName("pointer/310.png");
                    } else if (312.5d <= postTraces.getDirection() && postTraces.getDirection() < 317.5d) {
                        pointDTO.setImageName("pointer/315.png");
                    } else if (317.5d <= postTraces.getDirection() && postTraces.getDirection() < 322.5d) {
                        pointDTO.setImageName("pointer/320.png");
                    } else if (322.5d <= postTraces.getDirection() && postTraces.getDirection() < 327.5d) {
                        pointDTO.setImageName("pointer/325.png");
                    } else if (327.5d <= postTraces.getDirection() && postTraces.getDirection() < 332.5d) {
                        pointDTO.setImageName("pointer/330.png");
                    } else if (332.5d <= postTraces.getDirection() && postTraces.getDirection() < 337.5d) {
                        pointDTO.setImageName("pointer/335.png");
                    } else if (337.5d <= postTraces.getDirection() && postTraces.getDirection() < 342.5d) {
                        pointDTO.setImageName("pointer/340.png");
                    } else if (342.5d <= postTraces.getDirection() && postTraces.getDirection() < 347.5d) {
                        pointDTO.setImageName("pointer/345.png");
                    } else if (347.5d <= postTraces.getDirection() && postTraces.getDirection() < 352.5d) {
                        pointDTO.setImageName("pointer/350.png");
                    } else if (352.5d <= postTraces.getDirection() && postTraces.getDirection() < 357.5d) {
                        pointDTO.setImageName("pointer/355.png");
                    }
                }
            } else {
                pointDTO.setImageName("pointer/0.png");
            }
            pointDTO.setInfo("Пост ID: " + postTraces.getPost().getId());

            if (postTraces.getSpeed() != null) {
                pointDTO.setInfo(pointDTO.getInfo() + ". Скорость: " + postTraces.getSpeed().toString() + "km/h");
            }

            pointDTO.setInfo(pointDTO.getInfo() + ". Long: " + postTraces.getLongitude() + "; Lat: " + postTraces.getLatitude());

            return pointDTO;
        }
        return null;
    }

    @Override
    public String getImageNameForPost(Double direction) {
        if (direction != null) {
            if (357.5d <= direction || direction < 182.5d) {
                if (direction < 2.5d) {
                    return "pointer/0.png";
                }
                if (357.5d <= direction) {
                    return "pointer/0.png";
                } else if (2.5d <= direction && direction < 7.5d) {
                    return "pointer/5.png";
                } else if (7.5d <= direction && direction < 12.5d) {
                    return "pointer/10.png";
                } else if (12.5d <= direction && direction < 17.5d) {
                    return "pointer/15.png";
                } else if (17.5d <= direction && direction < 22.5d) {
                    return "pointer/20.png";
                } else if (22.5d <= direction && direction < 27.5d) {
                    return "pointer/25.png";
                } else if (27.5d <= direction && direction < 32.5d) {
                    return "pointer/30.png";
                } else if (32.5d <= direction && direction < 37.5d) {
                    return "pointer/35.png";
                } else if (37.5d <= direction && direction < 42.5d) {
                    return "pointer/40.png";
                } else if (42.5d <= direction && direction < 47.5d) {
                    return "pointer/45.png";
                } else if (47.5d <= direction && direction < 52.5d) {
                    return "pointer/50.png";
                } else if (52.5d <= direction && direction < 57.5d) {
                    return "pointer/55.png";
                } else if (57.5d <= direction && direction < 62.5d) {
                    return "pointer/60.png";
                } else if (62.5d <= direction && direction < 67.5d) {
                    return "pointer/65.png";
                } else if (67.5d <= direction && direction < 72.5d) {
                    return "pointer/70.png";
                } else if (72.5d <= direction && direction < 77.5d) {
                    return "pointer/75.png";
                } else if (77.5d <= direction && direction < 82.5d) {
                    return "pointer/80.png";
                } else if (82.5d <= direction && direction < 87.5d) {
                    return "pointer/85.png";
                } else if (87.5d <= direction && direction < 92.5d) {
                    return "pointer/90.png";
                } else if (92.5d <= direction && direction < 97.5d) {
                    return "pointer/95.png";
                } else if (97.5d <= direction && direction < 102.5d) {
                    return "pointer/100.png";
                } else if (102.5d <= direction && direction < 107.5d) {
                    return "pointer/105.png";
                } else if (107.5d <= direction && direction < 112.5d) {
                    return "pointer/110.png";
                } else if (112.5d <= direction && direction < 117.5d) {
                    return "pointer/115.png";
                } else if (117.5d <= direction && direction < 122.5d) {
                    return "pointer/120.png";
                } else if (122.5d <= direction && direction < 127.5d) {
                    return "pointer/125.png";
                } else if (127.5d <= direction && direction < 132.5d) {
                    return "pointer/130.png";
                } else if (132.5d <= direction && direction < 137.5d) {
                    return "pointer/135.png";
                } else if (137.5d <= direction && direction < 142.5d) {
                    return "pointer/140.png";
                } else if (142.5d <= direction && direction < 147.5d) {
                    return "pointer/145.png";
                } else if (147.5d <= direction && direction < 152.5d) {
                    return "pointer/150.png";
                } else if (152.5d <= direction && direction < 157.5d) {
                    return "pointer/155.png";
                } else if (157.5d <= direction && direction < 162.5d) {
                    return "pointer/160.png";
                } else if (162.5d <= direction && direction < 167.5d) {
                    return "pointer/165.png";
                } else if (167.5d <= direction && direction < 172.5d) {
                    return "pointer/170.png";
                } else if (172.5d <= direction && direction < 177.5d) {
                    return "pointer/175.png";
                } else if (177.5d <= direction && direction < 182.5d) {
                    return "pointer/180.png";
                }
            } else {
                if (182.5d <= direction && direction < 187.5d) {
                    return "pointer/185.png";
                } else if (187.5d <= direction && direction < 192.5d) {
                    return "pointer/190.png";
                } else if (192.5d <= direction && direction < 197.5d) {
                    return "pointer/195.png";
                } else if (197.5d <= direction && direction < 202.5d) {
                    return "pointer/200.png";
                } else if (202.5d <= direction && direction < 207.5d) {
                    return "pointer/205.png";
                } else if (207.5d <= direction && direction < 212.5d) {
                    return "pointer/210.png";
                } else if (212.5d <= direction && direction < 217.5d) {
                    return "pointer/215.png";
                } else if (217.5d <= direction && direction < 222.5d) {
                    return "pointer/220.png";
                } else if (222.5d <= direction && direction < 227.5d) {
                    return "pointer/225.png";
                } else if (227.5d <= direction && direction < 232.5d) {
                    return "pointer/230.png";
                } else if (232.5d <= direction && direction < 237.5d) {
                    return "pointer/235.png";
                } else if (237.5d <= direction && direction < 242.5d) {
                    return "pointer/240.png";
                } else if (242.5d <= direction && direction < 247.5d) {
                    return "pointer/245.png";
                } else if (247.5d <= direction && direction < 252.5d) {
                    return "pointer/250.png";
                } else if (252.5d <= direction && direction < 257.5d) {
                    return "pointer/255.png";
                } else if (257.5d <= direction && direction < 262.5d) {
                    return "pointer/260.png";
                } else if (262.5d <= direction && direction < 267.5d) {
                    return "pointer/265.png";
                } else if (267.5d <= direction && direction < 272.5d) {
                    return "pointer/270.png";
                } else if (272.5d <= direction && direction < 277.5d) {
                    return "pointer/275.png";
                } else if (277.5d <= direction && direction < 282.5d) {
                    return "pointer/280.png";
                } else if (282.5d <= direction && direction < 287.5d) {
                    return "pointer/285.png";
                } else if (287.5d <= direction && direction < 292.5d) {
                    return "pointer/290.png";
                } else if (292.5d <= direction && direction < 297.5d) {
                    return "pointer/295.png";
                } else if (297.5d <= direction && direction < 302.5d) {
                    return "pointer/300.png";
                } else if (302.5d <= direction && direction < 307.5d) {
                    return "pointer/305.png";
                } else if (307.5d <= direction && direction < 312.5d) {
                    return "pointer/310.png";
                } else if (312.5d <= direction && direction < 317.5d) {
                    return "pointer/315.png";
                } else if (317.5d <= direction && direction < 322.5d) {
                    return "pointer/320.png";
                } else if (322.5d <= direction && direction < 327.5d) {
                    return "pointer/325.png";
                } else if (327.5d <= direction && direction < 332.5d) {
                    return "pointer/330.png";
                } else if (332.5d <= direction && direction < 337.5d) {
                    return "pointer/335.png";
                } else if (337.5d <= direction && direction < 342.5d) {
                    return "pointer/340.png";
                } else if (342.5d <= direction && direction < 347.5d) {
                    return "pointer/345.png";
                } else if (347.5d <= direction && direction < 352.5d) {
                    return "pointer/350.png";
                } else if (352.5d <= direction && direction < 357.5d) {
                    return "pointer/355.png";
                }
            }
        }
        return "pointer/0.png";
    }


}
