package com.simona.model.dto;

import lombok.Data;

@Data
public class StationRserviceDTO {
    private Integer id;

//    private Set<Station> stations;

    private Integer sys_id;

    private String name;

    private RserviceTypesDTO rserviceTypesDTO;
}
