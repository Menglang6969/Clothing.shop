package com.menglang.Clothing.shop.dto.files.fileSize;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileSizeResponse {
    private Long originalValue;
    private Long formatValue;
    private String formatType;
    private String normalized;
}
