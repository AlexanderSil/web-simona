package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "RSERVICE_TYPES")
public class RserviceTypes {
    @Id
    private Integer id;

    private String name;

    private Integer type;

    @OneToMany(mappedBy="rserviceTypes")
    private Set<Rservice> rservices;
}
