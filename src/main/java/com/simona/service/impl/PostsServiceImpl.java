package com.simona.service.impl;

import com.simona.model.dto.PostDTO;
import com.simona.service.DaoService;
import com.simona.service.DtoService;
import com.simona.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private DtoService dtoService;

    @Autowired
    private DaoService daoService;


    @Override
    public List<PostDTO> getPostsDTO(Integer zoom, List<Integer> mrmsNames) {

        return daoService.getPostDTOs();
    }

    @Override
    public List<PostDTO> getActualPostsDTO(Integer zoom, List<Integer> mrmsNames) {
        return daoService.getPostDTOs();
    }

    @Override
    public void updatePostLocation(Double coordLat, Double coordLon, Integer speed, Double direction,
                                   Integer postID, String type, Integer packetID) {
        List<PostDTO> postDTOs = daoService.getPostDTOs();
        for (PostDTO postDTO : postDTOs) {
            if (postDTO.getId().equals(postID)) {
                postDTO.getLastPostTraces().setLatitude(coordLat);
                postDTO.getLastPostTraces().setLongitude(coordLon);
                postDTO.getLastPostTraces().setSpeed(speed);
                postDTO.getLastPostTraces().setDirection(direction);
                postDTO.setInfo("Пост ID: " + postDTO.getId()
                        + (speed!= null ? ". Скорость: " + speed + "km/h" : "" )
                        + ". Long: " + coordLon + "; Lat: " + coordLat);
                if (direction != null || direction != 0) {
                    postDTO.setImageName(dtoService.getImageNameForPost(direction));
                }
            }
        }
    }

}
