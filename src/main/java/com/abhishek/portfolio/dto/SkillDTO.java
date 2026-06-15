package com.abhishek.portfolio.dto;

import com.abhishek.portfolio.enums.SkillLevel;
import lombok.Data;

@Data
public class SkillDTO {
    private String name;
    private int yearsOfExperience;
    private SkillLevel level;
}