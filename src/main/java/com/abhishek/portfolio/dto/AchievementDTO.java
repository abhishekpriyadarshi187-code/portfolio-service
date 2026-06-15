package com.abhishek.portfolio.dto;

import com.abhishek.portfolio.enums.AchievementType;
import lombok.Data;

@Data
public class AchievementDTO {
    private String title;
    private String description;
    private String date;
    private AchievementType type;
}