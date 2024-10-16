package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exchange_rate")
@Builder
@Setter
@Getter
public class ExchangeRate extends BaseAuditEntity<Long> {

    @Column(name = "buying_price")
    private Double buyingPrice;

    @Column(name = "selling_price")
    private Double sellingPrice;
}
