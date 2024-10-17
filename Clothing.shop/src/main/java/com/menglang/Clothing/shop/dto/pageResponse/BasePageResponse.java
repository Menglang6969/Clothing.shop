package com.menglang.Clothing.shop.dto.pageResponse;

import java.io.Serializable;
import java.util.Date;

public class BasePageResponse implements Serializable {

    // Auditing fields
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
