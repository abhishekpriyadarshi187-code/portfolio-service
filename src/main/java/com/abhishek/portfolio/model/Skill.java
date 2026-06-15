package com.abhishek.portfolio.model;

import com.abhishek.portfolio.enums.SkillLevel;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    private String name;
    private int yearsOfExperience;
    private SkillLevel level; // BEGINNER, INTERMEDIATE, EXPERT
}