package com.simona.controller;

import com.simona.model.dto.PostDTO;
import com.simona.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/posts")
    public List<PostDTO> getPosts(@RequestParam(value = "zoom", required=false) Integer zoom,
                                           @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames) {
        if (mrmsNames != null) {
            return postsService.getPostsDTO(zoom, mrmsNames);
        } else {
            return new LinkedList<>();
        }
    }

    @GetMapping("/posts/actual")
    public List<PostDTO> getActualPosts(@RequestParam(value = "zoom", required=false) Integer zoom,
                                         @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames) {
        if (mrmsNames != null) {
            return postsService.getActualPostsDTO(zoom, mrmsNames);
        } else {
            return new LinkedList<>();
        }
    }

    @GetMapping("/posts/update")
    public List<PostDTO> updatePostLocation(@RequestParam(value = "zoom", required=false) Integer zoom,
                                             @RequestParam(value = "mrmsIds", required=false) List<Integer> mrmsNames,
                                             @RequestParam(value = "coordLat", required=false) Double coordLat,
                                             @RequestParam(value = "coordLon", required=false) Double coordLon,
                                             @RequestParam(value = "speed", required=false) Integer speed,
                                             @RequestParam(value = "direction", required=false) Double direction,
                                             @RequestParam(value = "postID", required=false) Integer postID,
                                             @RequestParam(value = "type", required=false) String type,
                                             @RequestParam(value = "packetID", required=false) Integer packetID) {

        log.info("Update Post Location| " + "{\"coord\":{\"lat\":" + coordLat + ",\"lon\":" + coordLon + "},\"speed\":" + speed +
                ",\"direction\":" + direction + ",\"postID\":" + postID + ",\"type\":\"" + type + "\",\"packetID\":" + packetID + "}");

        postsService.updatePostLocation(coordLat, coordLon, speed, direction, postID, type, packetID);

        if (mrmsNames != null) {
            return postsService.getActualPostsDTO(zoom, mrmsNames);
        } else {
            return new LinkedList<>();
        }
    }


}
