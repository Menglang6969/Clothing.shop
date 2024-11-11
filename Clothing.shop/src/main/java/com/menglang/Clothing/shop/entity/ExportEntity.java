package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exports")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExportEntity extends BaseAuditEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, unique = true,name = "export_no")
    private String exportNo;

    @ManyToOne
    @JoinColumn(name = "from_branch", nullable = false)
    private BranchEntity fromBranch;

    @ManyToOne
    @JoinColumn(name = "to_branch", nullable = false)
    private BranchEntity toBranch;

    @OneToMany(mappedBy = "exportRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ExportDetailsEntity> exportDetails = new HashSet<>();

}
