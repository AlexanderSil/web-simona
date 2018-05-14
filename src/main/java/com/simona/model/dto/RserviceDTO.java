package com.simona.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * It is for show in menu
 */
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RserviceDTO {

    private String name;
    private Integer count = 0;
    private String detected;//обнаружено
    private Integer detectedcount = 0;
    private String measured;//измерено
    private Integer measuredcount = 0;

}
