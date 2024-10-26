package com.menglang.Clothing.shop.utils;

import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
    public static String saveMultipartFile(MultipartFile file, String path) {
        if (file.getSize() <= 0) throw new CustomMessageException("No file found", "400");

        String filename = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String sourceFilename = (originalFilename != null && !originalFilename.isBlank()) ? originalFilename : file.getName();

        String extension=sourceFilename.contains(".")?sourceFilename.substring(sourceFilename.lastIndexOf(".")):"";
        Path pth=!path.isBlank()||!path.isEmpty()? Paths.get(path):Paths.get("./");

        try{
            if(Files.notExists(pth))
                Files.createDirectories(pth);
            String fullName=filename+extension;
            file.transferTo(pth.resolve(fullName));
            return fullName;

        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"400");
        }


    }
}
