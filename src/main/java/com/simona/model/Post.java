package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "POSTS")
public class Post {

    @Id
    private Integer id;

    private Integer state;

    private Date last_packet;

    @OneToMany(mappedBy="post")
    private Set<PostTraces> postTraces;
}
