package com.menglang.Clothing.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address_tbl")
public class AddressEntity extends BaseAuditEntity<Long>{

    @Column(unique = true, length = 60)
    private String street;

    @Column(length = 40,unique=true)
    private String city;

    @Column(unique=true,length=50)
    private String state;

    @Column(length = 15,name = "zip_code")
    private String zip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

}
