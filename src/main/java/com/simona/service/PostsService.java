package com.simona.service;

import com.simona.model.dto.PostDTO;

import java.util.List;

public interface PostsService {

    List<PostDTO> getPostsDTO(Integer zoom, List<Integer> mrmsNames);

    List<PostDTO> getActualPostsDTO(Integer zoom, List<Integer> mrmsNames);

    void updatePostLocation(Double coordLat, Double coordLon, Integer speed, Double direction, Integer postID, String type, Integer packetID);

}
