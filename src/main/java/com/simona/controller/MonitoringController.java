package com.simona.controller;

import com.simona.dao.BaseStationDao;
import com.simona.model.dto.RegionDto;
import com.simona.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alex on 3/19/18.
 */
@RestController
@RequestMapping(value = "/api")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/regions")
    public List<RegionDto> getRegions() {

        return monitoringService.getRegionsDto();
    }
}
