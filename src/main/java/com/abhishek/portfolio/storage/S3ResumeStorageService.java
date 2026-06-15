package com.abhishek.portfolio.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3ResumeStorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${app.aws.s3.resume-bucket}")
    private String bucketName;

    public String uploadResume(String userId, String templateId, MultipartFile file) {
        try {
            String key = String.format("resumes/%s/%s-%d.pdf",
                    userId,
                    templateId,
                    System.currentTimeMillis());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("application/pdf")
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return key;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to upload resume PDF to S3", ex);
        }
    }

    public String generatePresignedDownloadUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest)
                .url()
                .toString();
    }
}
