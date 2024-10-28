package com.menglang.Clothing.shop.services.file;

import com.menglang.Clothing.shop.dto.files.FileRequest;
import com.menglang.Clothing.shop.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    public FileEntity upload(MultipartFile file) throws Exception;
    public List<FileEntity> batchUpload(List<MultipartFile> files) throws Exception;
    public Page<FileEntity> findAll(int page,int pageSize,boolean isTrash,String query) throws Exception;
    public FileEntity restore(Long id) throws Exception;
    public FileEntity updateFileName(Long id, FileRequest fileRequest) throws Exception;
    public FileEntity delete(Long id) throws Exception;
    public FileEntity deleteFromTrash(Long id) throws Exception;
    public FileEntity forceDelete(Long id) throws Exception;
}
