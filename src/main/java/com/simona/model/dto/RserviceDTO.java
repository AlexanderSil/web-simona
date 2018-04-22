package com.simona.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * It is for show in menu
 */
@Component
@Getter
@Setter
public class RserviceDTO {

    private String name;
    private Integer count;

}
