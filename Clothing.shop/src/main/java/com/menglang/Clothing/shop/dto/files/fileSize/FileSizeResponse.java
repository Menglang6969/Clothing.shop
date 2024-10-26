package com.menglang.Clothing.shop.dto.files.fileSize;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FileSizeResponse {
    private Long originalValue;
    private Long formatValue;
    private String formatType;
    private String normalized;
}
