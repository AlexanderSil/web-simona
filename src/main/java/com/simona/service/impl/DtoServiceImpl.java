package com.simona.service.impl;

import com.simona.model.*;
import com.simona.model.dto.*;
import com.simona.service.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 3/24/18.
 */
@Service
public class DtoServiceImpl implements DtoService {

    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Override
    public List<RserviceDTO> getRserviceDTOs(Iterable<Rservice> rservices) {
        List<RserviceDTO> rserviceDTOs = new LinkedList<>();

        List<ControlPoint> controlPoints = toList(monitoringService.controlPoints);

        RserviceDTO rserviceDTO = new RserviceDTO();
        rserviceDTO.setName("Всего:");
        rserviceDTO.setCount(controlPoints.size());
        rserviceDTO.setDetected("Обнаружено:");
        rserviceDTO.setDetectedcount(controlPoints.size());
        rserviceDTO.setMeasured("Измерено:");
        rserviceDTO.setMeasuredcount(controlPoints.size());

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
                            if (controlPoint.getStatus() == 1) {// желтого цвета – РЭС выявлена (Обнаружено)
                                rserviceDTO.setDetectedcount(rserviceDTO.getDetectedcount() + 1);
                            } else if (controlPoint.getStatus() == 2) {// зеленого цвета – РЭС выявлена и измерена (Измерено)
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

    public static <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public PostDTO getPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setState(post.getState());
        if (post.getState() == null || post.getState() == 1) {
            postDTO.setIconName("blackCar.png");
        }
        if (post.getState() != null && post.getState() == 0) {
            postDTO.setIconName("greenCar.png");
        }
        postDTO.setName("РМ-1500-Р3/5М");//todo hardcode
        return postDTO;
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
}
