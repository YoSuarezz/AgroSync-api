package com.agrosync.application.secondaryports.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
public class Auditoria {

    @Column(name = "created_date")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @PrePersist
    private void onCreate() {
        createdDate = new Date();
    }

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @PreUpdate
    private void preUpdate() {
        modifiedDate = new Date();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
