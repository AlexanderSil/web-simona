package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "RSERVICES")
public class Rservice {
    @Id
    private Integer id;

    @OneToMany(mappedBy="rservice")
    private Set<Station> stations;

    private Integer sys_id;

    private String name;

    @ManyToOne
    @JoinColumn(name="type", nullable=false)
    private RserviceTypes rserviceTypes;
}
