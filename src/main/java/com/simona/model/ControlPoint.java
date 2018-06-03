package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "CONTROL_POINTS")
public class ControlPoint {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="stn_id", nullable=false)
    private Station station;

    private Integer stn_sys_id;
    private Double freq;
    private Integer status;
}
