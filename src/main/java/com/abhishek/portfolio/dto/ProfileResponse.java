package com.abhishek.portfolio.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileResponse {

    private String fullName;
    private String headline;
    private String about;

    private List<SkillDTO> skills;
    private List<ExperienceDTO> experiences;
    private List<EducationDTO> education;
    private List<ProjectDTO> projects;
    private List<AchievementDTO> achievements;
    private List<SocialLinkDTO> socialLinks;
}