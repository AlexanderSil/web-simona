package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    private Integer type;
}
