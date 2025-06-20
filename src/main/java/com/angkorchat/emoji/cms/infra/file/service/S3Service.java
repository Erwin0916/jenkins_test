package com.angkorchat.emoji.cms.infra.file.service;

import com.angkorchat.emoji.cms.global.util.NmpUtils;
import com.angkorchat.emoji.cms.infra.file.service.storage.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public String save(MultipartFile multipartFile, String dir) {
        String fileId = NmpUtils.getUniqueName();
        String format = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        String path = NmpUtils.createPath(fileId, format, dir);

        return amazonS3ResourceStorage.save(path, multipartFile, dir);
    }

    public void delete(String path) {
        try {
            amazonS3ResourceStorage.delete(path);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
