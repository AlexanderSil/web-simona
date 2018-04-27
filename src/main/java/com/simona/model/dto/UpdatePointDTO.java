package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UpdatePointDTO {
    private String postStatus;
    private Integer pointID;
    private String status;
}
