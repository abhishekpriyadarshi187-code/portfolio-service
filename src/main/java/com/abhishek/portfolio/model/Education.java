package com.abhishek.portfolio.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    private Institution institution;

    private String degree;
    private String fieldOfStudy;

    private int startYear;
    private int endYear;

    private String grade;
}