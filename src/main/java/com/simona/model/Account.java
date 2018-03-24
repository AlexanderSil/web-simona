package com.simona.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by alex on 3/14/18.
 */
@Entity
@Getter @Setter
public class Account implements Serializable {
    public Account(){}

    @Id
    @GeneratedValue
    private Long id;

    private String accountName;

    private String accountPassword;

    @Transient
    private String accountDetail;
}
