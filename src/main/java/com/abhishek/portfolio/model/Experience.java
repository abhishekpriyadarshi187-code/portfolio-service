package com.abhishek.portfolio.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    private String companyName;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
    private String description;
}