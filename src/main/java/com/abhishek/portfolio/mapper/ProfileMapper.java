package com.abhishek.portfolio.mapper;

import com.abhishek.portfolio.dto.*;
import com.abhishek.portfolio.model.*;
import com.abhishek.portfolio.security.CustomPrincipal;
import com.abhishek.portfolio.storage.S3ProfileImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final S3ProfileImageStorageService profileImageStorageService;

    public Profile toEntity(ProfileRequest request, CustomPrincipal principal) {
        return Profile.builder()
                .userId(principal.getUserId())
                .email(principal.getEmail())
                .mobileNumber(principal.getMobileNumber())
                .fullName(request.getFullName())
                .headline(request.getHeadline())
                .about(request.getAbout())
                .skills(mapSkills(request.getSkills()))
                .experiences(mapExperiences(request.getExperiences()))
                .education(mapEducation(request.getEducation()))
                .projects(mapProjects(request.getProjects()))
                .achievements(mapAchievements(request.getAchievements()))
                .socialLinks(mapSocialLinks(request.getSocialLinks()))
                .build();
    }

    public Profile updateEntity(Profile existing, ProfileRequest request, CustomPrincipal principal) {
        existing.setUserId(principal.getUserId());
        existing.setEmail(principal.getEmail());
        existing.setMobileNumber(principal.getMobileNumber());
        existing.setFullName(request.getFullName());
        existing.setHeadline(request.getHeadline());
        existing.setAbout(request.getAbout());
        existing.setSkills(mapSkills(request.getSkills()));
        existing.setExperiences(mapExperiences(request.getExperiences()));
        existing.setEducation(mapEducation(request.getEducation()));
        existing.setProjects(mapProjects(request.getProjects()));
        existing.setAchievements(mapAchievements(request.getAchievements()));
        existing.setSocialLinks(mapSocialLinks(request.getSocialLinks()));

        return existing;
    }

    public ProfileResponse toResponse(Profile profile) {
        return ProfileResponse.builder()
                .fullName(profile.getFullName())
                .headline(profile.getHeadline())
                .email(profile.getEmail())
                .mobileNumber(profile.getMobileNumber())
                .about(profile.getAbout())
                .profileImageUrl(profileImageStorageService.generatePresignedUrl(profile.getProfileImageKey()))
                .skills(mapSkillDTOs(profile.getSkills()))
                .experiences(mapExperienceDTOs(profile.getExperiences()))
                .education(mapEducationDTOs(profile.getEducation()))
                .projects(mapProjectDTOs(profile.getProjects()))
                .achievements(mapAchievementDTOs(profile.getAchievements()))
                .socialLinks(mapSocialLinkDTOs(profile.getSocialLinks()))
                .build();
    }

    private List<Skill> mapSkills(List<SkillDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> Skill.builder()
                        .name(dto.getName())
                        .yearsOfExperience(dto.getYearsOfExperience())
                        .level(dto.getLevel())
                        .build())
                .toList();
    }

    private List<SkillDTO> mapSkillDTOs(List<Skill> skills) {
        if (skills == null) return null;

        return skills.stream()
                .map(skill -> {
                    SkillDTO dto = new SkillDTO();
                    dto.setName(skill.getName());
                    dto.setYearsOfExperience(skill.getYearsOfExperience());
                    dto.setLevel(skill.getLevel());
                    return dto;
                })
                .toList();
    }

    private List<Experience> mapExperiences(List<ExperienceDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> Experience.builder()
                        .companyName(dto.getCompanyName())
                        .role(dto.getRole())
                        .startDate(parseDate(dto.getStartDate()))
                        .endDate(parseDate(dto.getEndDate()))
                        .current(dto.isCurrent())
                        .description(dto.getDescription())
                        .build())
                .toList();
    }

    private List<ExperienceDTO> mapExperienceDTOs(List<Experience> list) {
        if (list == null) return null;

        return list.stream()
                .map(exp -> {
                    ExperienceDTO dto = new ExperienceDTO();
                    dto.setCompanyName(exp.getCompanyName());
                    dto.setRole(exp.getRole());
                    dto.setStartDate(toString(exp.getStartDate()));
                    dto.setEndDate(toString(exp.getEndDate()));
                    dto.setCurrent(exp.isCurrent());
                    dto.setDescription(exp.getDescription());
                    return dto;
                })
                .toList();
    }

    private List<Education> mapEducation(List<EducationDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> Education.builder()
                        .institution(mapInstitution(dto.getInstitution()))
                        .degree(dto.getDegree())
                        .fieldOfStudy(dto.getFieldOfStudy())
                        .startYear(dto.getStartYear())
                        .endYear(dto.getEndYear())
                        .grade(dto.getGrade())
                        .build())
                .toList();
    }

    private List<EducationDTO> mapEducationDTOs(List<Education> list) {
        if (list == null) return null;

        return list.stream()
                .map(edu -> {
                    EducationDTO dto = new EducationDTO();
                    dto.setInstitution(mapInstitutionDTO(edu.getInstitution()));
                    dto.setDegree(edu.getDegree());
                    dto.setFieldOfStudy(edu.getFieldOfStudy());
                    dto.setStartYear(edu.getStartYear());
                    dto.setEndYear(edu.getEndYear());
                    dto.setGrade(edu.getGrade());
                    return dto;
                })
                .toList();
    }

    private Institution mapInstitution(InstitutionDTO dto) {
        if (dto == null) return null;

        return Institution.builder()
                .name(dto.getName())
                .type(dto.getType())
                .location(dto.getLocation())
                .build();
    }

    private InstitutionDTO mapInstitutionDTO(Institution entity) {
        if (entity == null) return null;

        InstitutionDTO dto = new InstitutionDTO();
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setLocation(entity.getLocation());
        return dto;
    }

    private List<Project> mapProjects(List<ProjectDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> Project.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .techStack(dto.getTechStack())
                        .link(dto.getLink())
                        .build())
                .toList();
    }

    private List<ProjectDTO> mapProjectDTOs(List<Project> list) {
        if (list == null) return null;

        return list.stream()
                .map(p -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setTitle(p.getTitle());
                    dto.setDescription(p.getDescription());
                    dto.setTechStack(p.getTechStack());
                    dto.setLink(p.getLink());
                    return dto;
                })
                .toList();
    }

    private List<Achievement> mapAchievements(List<AchievementDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> Achievement.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .date(parseDate(dto.getDate()))
                        .type(dto.getType())
                        .build())
                .toList();
    }

    private List<AchievementDTO> mapAchievementDTOs(List<Achievement> list) {
        if (list == null) return null;

        return list.stream()
                .map(a -> {
                    AchievementDTO dto = new AchievementDTO();
                    dto.setTitle(a.getTitle());
                    dto.setDescription(a.getDescription());
                    dto.setDate(toString(a.getDate()));
                    dto.setType(a.getType());
                    return dto;
                })
                .toList();
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> SocialLink.builder()
                        .platform(dto.getPlatform())
                        .url(dto.getUrl())
                        .build())
                .toList();
    }

    private List<SocialLinkDTO> mapSocialLinkDTOs(List<SocialLink> list) {
        if (list == null) return null;

        return list.stream()
                .map(s -> {
                    SocialLinkDTO dto = new SocialLinkDTO();
                    dto.setPlatform(s.getPlatform());
                    dto.setUrl(s.getUrl());
                    return dto;
                })
                .toList();
    }

    private LocalDate parseDate(String date) {
        return date != null ? LocalDate.parse(date) : null;
    }

    private String toString(LocalDate date) {
        return date != null ? date.toString() : null;
    }
}