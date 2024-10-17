package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "imports")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ImportEntity extends BaseAuditEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true)
    private String importNo;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;

    @OneToMany(mappedBy = "import_record")
    @Builder.Default
    private Set<ImportDetailsEntity> importDetails = new HashSet<>();

}
