package com.abhishek.portfolio.dto;

import lombok.Data;

@Data
public class EducationDTO {

    private InstitutionDTO institution;

    private String degree;
    private String fieldOfStudy;
    private int startYear;
    private int endYear;
    private String grade;
}