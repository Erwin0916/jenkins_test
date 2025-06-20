package com.angkorchat.emoji.cms.infra.file.service.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.angkorchat.emoji.cms.global.util.NmpUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private static final Logger log = LoggerFactory.getLogger(AmazonS3ResourceStorage.class);

    public String save(String fullPath, MultipartFile multipartFile, String dir) {
        String fileUname = NmpUtils.getUniqueName() + "." + NmpUtils.getFormat(multipartFile.getContentType());
        String urlPath = NmpUtils.FILE_SEPARATOR + dir + NmpUtils.FILE_SEPARATOR + NmpUtils.getTodayString();
        String uploadPath = NmpUtils.UPLOAD_PATH + urlPath;
        String fileSavePath = uploadPath + NmpUtils.FILE_SEPARATOR + fileUname;
        File file = new File(uploadPath);

        log.info("\n\nfile: " + file + "\n");

        try {
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(fileSavePath);
            multipartFile.transferTo(file);

            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file).withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3Client.getUrl(bucket, fullPath).toString();
        } catch (Exception ex) {
            log.error("\n\nError message: " + ex.getMessage() + "\n");
            throw new RuntimeException();
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void delete(String path) throws MalformedURLException {
        try {
            URL url = new URL(path);
            path = url.getPath().substring(1);

            if (amazonS3Client.doesObjectExist(bucket, path)) {
                amazonS3Client.deleteObject(bucket, path);
            }
        } catch (Exception ex) {
            throw new MalformedURLException();
        }
    }
}
