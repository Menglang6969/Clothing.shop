package com.menglang.Clothing.shop.services.Storage;

import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    public static final String FILE_PATH = System.getProperty("user.dir") + "/uploads";

    public String upload(MultipartFile file) {
        return FileUtil.saveMultipartFile(file, FILE_PATH);
    }

    public void loadFile(String filename, HttpServletResponse response) {
        try {
            Path p = Paths.get(FILE_PATH).resolve(filename).toAbsolutePath().normalize();//convert filename to Path Obj & concat to filepath (location in fileSystem)
            Resource resource = new UrlResource(p.toUri());//convert path Obj to URI //access resource via url
            if (!resource.exists() || !resource.isReadable()) {
                throw new CustomMessageException("file cannot read", "404");
            }

            response.setHeader(HttpHeaders.CONTENT_TYPE, Files.probeContentType(p));
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(Files.size(p)));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"inline;filename="+"\""+filename+"\"");

            FileCopyUtils.copy(resource.getInputStream(),response.getOutputStream());


        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }


}
