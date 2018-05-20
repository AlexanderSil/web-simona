package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
public class PostTracesDTO {
    private Integer id;

//    private PostDTOTemp post;

    private LocalDateTime timestamp;

    private Double latitude;
    private Double longitude;

    private Integer speed;
    private Double direction;
}
