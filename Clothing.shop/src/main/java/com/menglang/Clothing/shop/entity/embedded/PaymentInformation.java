package com.menglang.Clothing.shop.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class PaymentInformation {

    @Column(length = 50)
    private String card_holder_name;

    @Column(length = 30)
    private String card_number;

    @Column(length = 20)
    private String expiry_date;

    @Column(length = 10)
    private String cvv;
}
