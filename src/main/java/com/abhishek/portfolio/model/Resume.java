package com.abhishek.portfolio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "resumes")
public class Resume {

    @Id
    private String id;

    private String userId;
    private String templateId;
    private String resumeFileKey;
    private LocalDateTime lastGeneratedAt;
}
