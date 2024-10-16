package com.menglang.Clothing.shop.dto.pageResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.dto.pageResponse.body.BodyResponse;
import com.menglang.Clothing.shop.dto.pageResponse.page.PageResponse;
import com.menglang.Clothing.shop.dto.pageResponse.status.StatusResponse;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class BaseResponse implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(BaseResponse.class);
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Boolean success;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private BodyResponse body;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private StatusResponse status;

    public static ResponseEntity<BaseResponse> successful(Page<BasePageResponse> res,String message) {
        List<BasePageResponse> data = res.getContent();

        PageResponse page;
        if (res.getPageable().isUnpaged()) {
            page = null;
        } else page = PageResponse.builder()
                .count(res.getTotalElements())
                .size(res.getSize())
                .totalPage((int) (res.getTotalElements()/res.getSize())+1)
                .page(res.getNumber()+1)
                .build();

        StatusResponse status=StatusResponse.builder()
                .message(message)
                .status((short) 200)
                .build();
        return ResponseEntity.ok(BaseResponse.builder().body(BodyResponse.builder().data(data).page(page).build()).success(true).status(status).build());
    }

    public static ResponseEntity<BaseResponse> success(Object data, Page<?> page, String message) {
        short code = 200;
        StatusResponse status = StatusResponse.builder().message(message).status(code).build();
        BodyResponse bodyData = BodyResponse.builder().build();



        BaseResponse res = BaseResponse.builder().build();
        log.info(" data res: {}",data.toString());
        log.info(" data page: {}",page);
        if (data instanceof BodyResponse) {
            bodyData.setData(data);
        } else if (data instanceof List<?>) {
            if (!((List<?>) data).isEmpty() && ((List<?>) data).get(0) instanceof BodyResponse) {
                bodyData.setData(data);
            } else {
                bodyData.setData(Collections.emptyList());
            }
        }

        if (page != null) {
            PageResponse pageResponse = PageResponse.builder().build();
            pageResponse.setSize(page.getSize());
            pageResponse.setPage(page.getNumber());
            pageResponse.setCount(page.getTotalElements());
            pageResponse.setTotalPage((int) (page.getTotalElements() / page.getSize()));

            bodyData.setPage(pageResponse);
        }

        res.setBody(bodyData);
        res.setStatus(status);
        res.setSuccess(true);

        return ResponseEntity.ok(res);

    }



    public static ResponseEntity<BaseResponse> failed(String message, HttpStatusCode httpStatus) {
        BaseResponse data = BaseResponse.builder().build();
        StatusResponse statusResponse =  StatusResponse.builder().build();
        statusResponse.setMessage(message);
        statusResponse.setStatus((short) httpStatus.value());

        data.setSuccess(false);
        data.setStatus(statusResponse);
        return ResponseEntity.status(httpStatus).body(data);
    }
}
