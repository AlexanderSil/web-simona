package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "POST_TRACES")
public class PostTraces {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pst_id", nullable=false)
    private Post post;

    private Date timestamp;

    private Double latitude;
    private Double longitude;

    private Integer speed;
    private Integer direction;
}