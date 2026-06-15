package com.abhishek.portfolio.service.impl;

import com.abhishek.portfolio.dto.ResumeResponse;
import com.abhishek.portfolio.model.Resume;
import com.abhishek.portfolio.repository.ResumeRepository;
import com.abhishek.portfolio.service.ResumeService;
import com.abhishek.portfolio.storage.S3ResumeStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final S3ResumeStorageService s3ResumeStorageService;

    @Override
    public ResumeResponse getResume(String userId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElse(null);

        return toResponse(resume, false);
    }

    @Override
    public ResumeResponse saveTemplate(String userId, String templateId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElse(Resume.builder()
                        .userId(userId)
                        .build());

        resume.setTemplateId(templateId);
        resume.setLastGeneratedAt(LocalDateTime.now());

        Resume saved = resumeRepository.save(resume);
        return toResponse(saved, false);
    }

    @Override
    public ResumeResponse uploadResumePdf(String userId, String templateId, MultipartFile file) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElse(Resume.builder()
                        .userId(userId)
                        .build());

        String key = s3ResumeStorageService.uploadResume(userId, templateId, file);

        resume.setTemplateId(templateId);
        resume.setResumeFileKey(key);
        resume.setLastGeneratedAt(LocalDateTime.now());

        Resume saved = resumeRepository.save(resume);
        return toResponse(saved, true);
    }

    @Override
    public ResumeResponse getDownloadUrl(String userId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        return toResponse(resume, true);
    }

    private ResumeResponse toResponse(Resume resume, boolean includeDownloadUrl) {
        if (resume == null) {
            return null;
        }

        return ResumeResponse.builder()
                .userId(resume.getUserId())
                .templateId(resume.getTemplateId())
                .resumeFileKey(resume.getResumeFileKey())
                .lastGeneratedAt(resume.getLastGeneratedAt())
                .downloadUrl(includeDownloadUrl && resume.getResumeFileKey() != null
                        ? s3ResumeStorageService.generatePresignedDownloadUrl(resume.getResumeFileKey())
                        : null)
                .build();
    }

}