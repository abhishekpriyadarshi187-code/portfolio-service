package com.abhishek.portfolio.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private String title;
    private String description;
    private List<String> techStack;
    private String link;
}