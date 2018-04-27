package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "STATIONS")
public class Station {
    @Id
    private Integer id;

    @OneToMany(mappedBy="station")
    private Set<ControlPoint> controlPoints;

    private Integer sys_id;

    @ManyToOne
    @JoinColumn(name="rsrvc_id", nullable=false)
    private Rservice rservice;

    private Integer rsrvc_sys_id;
    private Integer entrp_id;
    private Integer entrp_sys_id;
    private Double latitude;
    private Double longitude;
    private String region;
    private String city;
    private String address;
    private String nick_name;
    private String nick_name_index;
    private Integer status;
    private String perm_number;
    private Date perm_date_from;
    private Date perm_date_to;
    private String perm_remark;
}
