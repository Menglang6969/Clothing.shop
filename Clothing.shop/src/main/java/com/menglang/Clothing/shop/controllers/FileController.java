package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.constant.AppProperties;
import com.menglang.Clothing.shop.dto.files.FileMapper;
import com.menglang.Clothing.shop.dto.files.FileRequest;
import com.menglang.Clothing.shop.dto.files.FileResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.FileEntity;
import com.menglang.Clothing.shop.services.Storage.StorageService;
import com.menglang.Clothing.shop.services.file.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/admin/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;
    private final StorageService storageService;
    private final FileMapper fileMapper;
    private final AppProperties appProperties;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse> upload(@RequestPart MultipartFile file) throws Exception {
        log.info("invoke upload file....");
        return BaseResponse.success(fileMapper.toFilerResponse(fileService.upload(file)), null, "Success");
    }


    @PostMapping(value = "batch-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse> batchUpload(@RequestPart List<MultipartFile> files) throws Exception {
        return BaseResponse.success(fileMapper.toFilerResponseList(fileService.batchUpload(files)), null, "Success");
    }

    @PatchMapping("/update-file-name/{id}")
    public ResponseEntity<BaseResponse> updateFileName(@PathVariable Long id, @RequestBody FileRequest request) throws Exception {
        FileEntity data = this.fileService.updateFileName(id, request);

        return BaseResponse.success(fileMapper.toFilerResponse(data), null, "Update Successfully");
    }

    @GetMapping("/load/{filename}")
    public void loadFile(@PathVariable String filename, HttpServletResponse response) {
        this.storageService.loadFile(filename, response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAllFiles(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "isTrash", defaultValue = "false") boolean isTrash,
            @RequestParam(required = false, name = "query", defaultValue = "") String query
    )       throws Exception {
        Page<FileEntity> page_Entity=fileService.findAll(page, limit, isTrash, query);
        List<FileResponse> pageResponse = page_Entity.stream().map(fileMapper::toFilerResponse).toList();
        return BaseResponse.success(pageResponse, page_Entity, "Success");
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<BaseResponse> deleteFile(@PathVariable("id") Long id) throws Exception {
        return BaseResponse.success(this.fileMapper.toFilerResponse(this.fileService.delete(id)), null, "Success");
    }

    @PutMapping("restore/{id}")
    public ResponseEntity<BaseResponse> restoreFile(@PathVariable("id") Long id) throws Exception {
        return BaseResponse.success(this.fileMapper.toFilerResponse(this.fileService.restore(id)), null, "Success");
    }

    @DeleteMapping("force-delete/{id}")
    public ResponseEntity<BaseResponse> forceDeleteFile(@PathVariable("id") Long id) throws Exception {
        return BaseResponse.success(this.fileMapper.toFilerResponse(this.fileService.forceDelete(id)), null, "Success");
    }



}
