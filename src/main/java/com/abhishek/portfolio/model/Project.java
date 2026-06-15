package com.abhishek.portfolio.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private String title;
    private String description;
    private List<String> techStack;
    private String link;
}