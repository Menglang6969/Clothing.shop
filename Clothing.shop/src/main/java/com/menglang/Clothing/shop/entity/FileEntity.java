package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "files")
public class FileEntity extends BaseAuditEntity<Long> {

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,name = "original_name")
    private String originalName;

    @Column(nullable = false,length = 20)
    private String type;

    @Column(nullable = false)
    private Long size;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileEntity that = (FileEntity) object;
        return Objects.equals(name, that.name) && Objects.equals(originalName, that.originalName) && Objects.equals(type, that.type) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, originalName, type, size);
    }
}
