package com.menglang.Clothing.shop.services.file;

import com.menglang.Clothing.shop.dto.files.FileRequest;
import com.menglang.Clothing.shop.entity.FileEntity;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.FileRepository;
import com.menglang.Clothing.shop.services.Storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Autowired
    private final StorageService storageService;
    @Autowired
    private final FileRepository fileRepository;

    @Override
    public FileEntity upload(MultipartFile file) throws Exception {
        String fileName = storageService.upload(file);//move file to folder and response generate filename (uuid.ext)
        FileEntity f = FileEntity.builder().name(fileName).originalName(file.getOriginalFilename()).size(file.getSize()).type(file.getContentType()).build();
        try {
            return this.fileRepository.save(f);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<FileEntity> batchUpload(List<MultipartFile> files) throws Exception {
        List<FileEntity> batchFile = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                batchFile.add(this.upload(file));
            }
            return batchFile;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Page<FileEntity> findAll(int page, int pageSize, boolean isTrash, String query) throws Exception {
        if (page <= 0 || pageSize <= 0) throw new CustomMessageException("Page and limit must be greater than 0", "403");
        Sort sort= Sort.by(Sort.Direction.ASC, "originalName");
        Pageable pageable= PageRequest.of(page-1,pageSize,sort);
        if (isTrash) {
            return this.fileRepository.findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNotNull(query, pageable);
        }
       return this.fileRepository.findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNull(query,pageable);

    }

    @Override
    public FileEntity restore(Long id) throws Exception {
        FileEntity file=this.fileRepository.findByIdAndDeletedAtIsNotNull(id);
        if (file!=null){
            file.setDeletedAt(null);
            try{
                return this.fileRepository.save(file);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }else {
            throw new NotFoundException("File not found");
        }


    }

    @Override
    public FileEntity updateFileName(Long id, FileRequest fileRequest) throws Exception {
        FileEntity existFile = this.findById(id);
        if (fileRequest.name().isEmpty() || fileRequest.name().isBlank()) {
            throw new CustomMessageException("File Is Blank", "400");
        }
        existFile.setOriginalName(fileRequest.name());
        try {
            return fileRepository.save(existFile);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public FileEntity delete(Long id) throws Exception {
        FileEntity existFile = this.findById(id);
        existFile.setDeletedAt(new Date());
        try{
          return this.fileRepository.save(existFile);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public FileEntity deleteFromTrash(Long id) throws Exception {
        FileEntity existFile = this.findById(id);
        try {
            storageService.deleteFile(existFile.getOriginalName());
            fileRepository.delete(existFile);
            return existFile;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public FileEntity forceDelete(Long id) throws Exception {
        FileEntity existFile = this.findById(id);
        try {
            fileRepository.delete(existFile);
            return existFile;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private FileEntity findById(Long id) throws Exception {
        return this.fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File not found"));
    }
}
