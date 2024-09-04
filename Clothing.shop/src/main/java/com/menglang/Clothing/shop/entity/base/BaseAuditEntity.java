package com.menglang.Clothing.shop.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity <T extends Serializable> extends BaseEntity<T> {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",nullable = false,updatable = false)
    @CreatedDate
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date",insertable = false)
    @LastModifiedDate
    private Date updateAt;

    @Column(name = "created_by",length = 40,updatable=false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by",insertable = false)
    @LastModifiedBy
    private String updatedBy;

}
