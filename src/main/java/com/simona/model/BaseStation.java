package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alex on 3/19/18.
 */
@Entity
@Table(name = "base_station")
@Getter @Setter
public class BaseStation {
    @Id
    @GeneratedValue
    private Long id;

    private Double latitude;
    private Double longitude;

    private String type;

}
