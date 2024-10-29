package com.menglang.Clothing.shop.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(index = 1)
    private Long id;
    @JsonProperty(index = 2)
    private String name;
    @JsonProperty(index = 3)
    private String originalName;
    @JsonProperty(index = 4)
    private String url;
    @JsonProperty(index = 5)
    private FileSizeResponse size;
    @JsonProperty(index = 6)
    private String type;
    @JsonProperty(index = 7)
    private Boolean isTrash;

    public FileResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}
