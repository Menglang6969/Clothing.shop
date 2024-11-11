package com.menglang.Clothing.shop.entity;


import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role_tbl")
public class RoleEntity extends BaseAuditEntity<Long> {


    @Column(unique=true,length=30)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<UserEntity> user=new HashSet<>();

}
