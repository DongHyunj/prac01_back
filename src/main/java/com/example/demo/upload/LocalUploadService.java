package com.example.demo.upload;

import com.example.demo.utils.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// @Primary
@RequiredArgsConstructor
// @Service
public class LocalUploadService implements UploadService{
    private final UploadUtil uploadUtil;

    @Value("${project.upload.path}")
    private String defaultUploadPath;

    private String saveFile(MultipartFile file) {
        String uploadPath = uploadUtil.makeFolder();

        String filePath = uploadPath + File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();

        File saveFile = new File(filePath);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }

    @Override
    public List<String> upload(List<MultipartFile> fileList) {
        List<String> uploadPathList = new ArrayList<>();

//        for(MultipartFile file : fileList) {
//            String uploadPath = saveFile(file);
//            uploadPathList.add(uploadPath);
//        }
//        return uploadPathList;

        // TODO: 어떤 게시글의 이미지인지 DB에 저장하는지 기능 만들기

        return fileList.stream().map(this::saveFile).toList();
    }
}
