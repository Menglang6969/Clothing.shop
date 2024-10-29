package com.menglang.Clothing.shop.exceptions;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity<ResponseTemplate> handleCustomMessageException(final CustomMessageException e) {
        return ResponseEntity.ok(new ResponseTemplate(e.getMessage(), e.getCode(), new Object()));
    }


    @ExceptionHandler(value = {Exception.class, InternalServerErrorException.class})
    public ResponseEntity<BaseResponse> handleInternalServerErrorException(InternalServerErrorException ex){
        return BaseResponse.failed(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException ex){
        return BaseResponse.failed(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotFoundException.class})
    public ResponseEntity<BaseResponse> handleNotfoundException(NotFoundException ex){
        return BaseResponse.failed(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ConflictException.class})
    public ResponseEntity<BaseResponse> handleNotfoundException(ConflictException ex){
        return BaseResponse.failed(ex.getMessage(),HttpStatus.NOT_FOUND);
    }




}
