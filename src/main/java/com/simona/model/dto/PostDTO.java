package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class PostDTO {

    private Integer id;

    private Integer state;

    private Date last_packet;

    private List<PostTracesDTO> postTraces;

    private PostTracesDTO lastPostTraces;

    private String imageName;

    private String info;
}
