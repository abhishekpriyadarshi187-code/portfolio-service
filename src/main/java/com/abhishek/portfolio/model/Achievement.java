package com.abhishek.portfolio.model;

import com.abhishek.portfolio.enums.AchievementType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    private String title;
    private String description;
    private LocalDate date;
    private AchievementType type; // ACADEMIC / PROFESSIONAL / PERSONAL
}