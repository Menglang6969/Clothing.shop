package com.menglang.Clothing.shop.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class Size {


    private int qty;

    private String name;

}
