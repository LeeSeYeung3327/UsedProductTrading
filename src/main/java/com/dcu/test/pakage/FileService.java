package com.dcu.test.pakage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    public String imageSave(MultipartFile image) throws IOException {
        String defaultImagePath = "/upload/images/default-image.png"; // 기본 이미지 경로

        // 파일이 없거나 비어있으면 기본 이미지 반환
        if (image == null || image.isEmpty()) {
            return defaultImagePath;
        }

        Path uploadPath = Paths.get("C:/JAVA/test/uploads/images/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(image.getInputStream(), filePath);

        return "/upload/images/" + fileName;
    }

    public void fileDelete(String file) throws IOException {
        Files.deleteIfExists(Paths.get("./upload/images").resolve(file));
    }

}
