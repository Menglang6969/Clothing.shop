package com.menglang.Clothing.shop.services.file;

import com.menglang.Clothing.shop.dto.files.FileRequest;
import com.menglang.Clothing.shop.entity.FileEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.FileRepository;
import com.menglang.Clothing.shop.services.Storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    @Autowired
    private final StorageService storageService;
    @Autowired
    private final FileRepository fileRepository;

    @Override
    public FileEntity upload(MultipartFile file) throws Exception {
        String fileName=storageService.upload(file);//move file to folder and response generate filename (uuid.ext)
        FileEntity f=FileEntity.builder()
                .name(fileName)
                .originalName(file.getOriginalFilename())
                .size(file.getSize())
                .type(file.getContentType())
                .build();
       try{
           return this.fileRepository.save(f);
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"400");
       }
    }

    @Override
    public List<FileEntity> batchUpload(List<MultipartFile> files) throws Exception {
        List<FileEntity> batchFile=new ArrayList<>();
      try{
          for (MultipartFile file:files){
            batchFile.add(this.upload(file));
          }
          return batchFile;
      }catch (Exception e){
          throw new CustomMessageException(e.getMessage(),"400");
      }

    }

    @Override
    public Page<FileEntity> findAll(int page, int pageSize, boolean isTrash, String query) throws Exception {
        return null;
    }

    @Override
    public FileEntity restore(Long id) throws Exception {
        return null;
    }

    @Override
    public FileEntity updateFileName(Long id, FileRequest fileRequest) throws Exception {
        return null;
    }

    @Override
    public FileEntity delete(Long id) throws Exception {
        return null;
    }

    @Override
    public FileEntity deleteFromTrash(Long id) throws Exception {
        return null;
    }

    @Override
    public FileEntity forceDelete(Long id) throws Exception {
        return null;
    }
}
