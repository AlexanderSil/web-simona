package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControlPointDTO {
    private Integer id;
//    private Station station;
    private Integer stn_sys_id;
    private Double freq;
    private Integer status;
}
