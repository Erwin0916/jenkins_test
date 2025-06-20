package com.angkorchat.emoji.cms.domain.studio.file.service;

import com.angkorchat.emoji.cms.domain.studio.file.dto.response.*;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import com.angkorchat.emoji.cms.infra.file.service.S3Service;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class StudioFileService {
    private final S3Service s3Service;

    public StudioFileResponse studioUploadFile(MultipartFile file, String folderType) {
        StudioFileResponse res = new StudioFileResponse();

        if(folderType.equals("Emoji")) {
            String url = s3Service.save(file, "emoji");

            res.setUrl(url);
            res.setFileName(file.getOriginalFilename());
            res.setFileType(file.getContentType());
        } else if (folderType.equals("Artist")) {
            String url = s3Service.save(file, "artist");

            res.setUrl(url);
            res.setFileName(file.getOriginalFilename());
            res.setFileType(file.getContentType());
        } else throw new InvalidInputDataFormatException("folderType");

        return res;
    }
}
