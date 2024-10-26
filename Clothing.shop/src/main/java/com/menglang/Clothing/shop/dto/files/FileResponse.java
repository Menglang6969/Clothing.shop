package com.menglang.Clothing.shop.dto.files;

import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import com.menglang.Clothing.shop.dto.files.fileSize.FileSizeResponse;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

public class FileResponse extends BaseResponseAudit {
    private Long id;
    private String name;
    private String originalName;
    private String url;
    private FileSizeResponse size;
    private String type;
    private Boolean isTrash;

    public FileResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}
