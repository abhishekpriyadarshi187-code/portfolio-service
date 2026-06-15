package com.abhishek.portfolio.service;

import com.abhishek.portfolio.dto.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    ResumeResponse getResume(String userId);
    ResumeResponse saveTemplate(String userId, String templateId);
    ResumeResponse uploadResumePdf(String userId, String templateId, MultipartFile file);
    ResumeResponse getDownloadUrl(String userId);
}