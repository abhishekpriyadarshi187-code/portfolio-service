package com.abhishek.portfolio.dto;

import lombok.Data;

@Data
public class ExperienceDTO {
    private String companyName;
    private String role;
    private String startDate;
    private String endDate;
    private boolean current;
    private String description;
}