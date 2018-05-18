package com.simona.controller;

import com.simona.model.dto.PostDTOTemp;
import com.simona.model.dto.RegionDTO;
import com.simona.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/regions")
    public List<RegionDTO> getRegions() {
        return menuService.getRegionsDTO();
    }


    @GetMapping("/region/actual/info")
    public List<RegionDTO> updatePostInfo() {
        return menuService.getActualRegions();
    }

    @GetMapping("/updatePostStatus")
    public HttpStatus updatePostStatus(@RequestParam(value = "status",required=false) String status,
                                       @RequestParam(value = "postID",required=false) Integer postID,
                                       @RequestParam(value = "type",required=false) String type,
                                       @RequestParam(value = "packetID",required=false ) Integer packetID) {
        log.info("Update Post Status| " + "{\"status\":\"" + status + "\",\"postID\":" + postID + ",\"type\":\"" + type + "\",\"packetID\":" + packetID + "}");

        menuService.updatePostStatus(postID, status);
        return HttpStatus.OK;
    }


}
