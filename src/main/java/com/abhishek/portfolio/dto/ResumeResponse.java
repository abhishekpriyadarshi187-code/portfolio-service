package com.abhishek.portfolio.dto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeResponse {
    private String userId;
    private String templateId;
    private String resumeFileKey;
    private LocalDateTime lastGeneratedAt;
    private String downloadUrl;
}