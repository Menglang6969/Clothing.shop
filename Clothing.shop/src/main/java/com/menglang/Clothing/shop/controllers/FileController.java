package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.entity.FileEntity;
import com.menglang.Clothing.shop.services.Storage.StorageService;
import com.menglang.Clothing.shop.services.file.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/admin/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @PostMapping(value ="upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileEntity> upload(@RequestPart MultipartFile file)throws Exception{
        log.info("invoke upload file....");
        return ResponseEntity.ok(fileService.upload(file));
    }


//
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<List<FileEntity>> batchUpload(@RequestPart List<MultipartFile> files) throws Exception {
//        return ResponseEntity.ok(fileService.batchUpload(files));
//    }


}
