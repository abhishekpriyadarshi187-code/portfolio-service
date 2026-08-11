package com.abhishek.portfolio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private String id;
    private String userId; // from JWT
    private String email;
    private String profileImageKey;
    private String mobileNumber;

    private String fullName;
    private String headline;
    private String about;

    private List<Skill> skills;
    private List<Experience> experiences;
    private List<Education> education;
    private List<Project> projects;
    private List<Achievement> achievements;
    private List<SocialLink> socialLinks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}