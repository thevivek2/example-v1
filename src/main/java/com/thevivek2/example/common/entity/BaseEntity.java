package com.thevivek2.example.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thevivek2.example.common.constant.Constants;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ACTIVE_STATUS")
    private String activeStatus = Constants.ACTIVE;

    @JsonIgnore
    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    @JsonIgnore
    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate = LocalDateTime.now();

    @JsonIgnore
    @Column(name = "ADDED_BY", updatable = false)
    private Integer addedBy;

    @JsonIgnore
    @Column(name = "ADDED_DATE", updatable = false)
    private LocalDateTime addedDate = LocalDateTime.now();


    @PrePersist
    public void prePersist() {
        this.addedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

